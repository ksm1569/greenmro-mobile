package com.smsoft.greenmromobile.domain.order.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smsoft.greenmromobile.domain.order.dto.OrderListRequestDto;
import com.smsoft.greenmromobile.domain.order.dto.OrderListResponseDto;
import com.smsoft.greenmromobile.domain.order.entity.QOrderItems;
import com.smsoft.greenmromobile.domain.order.entity.QSalesOrder;
import com.smsoft.greenmromobile.domain.product.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderListResponseDto> findOrderSummariesByUserId(Long userId, OrderListRequestDto orderListRequestDto, Pageable pageable) {
        QOrderItems orderItems = QOrderItems.orderItems;
        QSalesOrder salesOrder = QSalesOrder.salesOrder;
        QProduct product = QProduct.product;

        // 1.상품명 / 2.일자 / 3.아이디 -> 동적쿼리 필터
        BooleanBuilder filter = new BooleanBuilder();
        if (orderListRequestDto.productName() != null && !orderListRequestDto.productName().isEmpty()) {
            filter.and(product.pname.containsIgnoreCase(orderListRequestDto.productName()));
        }
        filter.and(salesOrder.soDate.between(orderListRequestDto.startDate().atStartOfDay(), orderListRequestDto.endDate().atTime(LocalTime.MAX)));
        filter.and(orderItems.ownerUserId.eq(userId));

        List<Long> ids = queryFactory
                .select(orderItems.orefItem)
                .from(orderItems)
                .leftJoin(salesOrder).on(orderItems.salesOrder.sorefItem.eq(salesOrder.sorefItem))
                .where(filter)
                .orderBy(salesOrder.soDate.desc())
                .fetch();

        // 오라클 11g 별도 페이징
        List<Long> paginatedIds = ids.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();

        // 최종 쿼리 실행
        return queryFactory
                .select(Projections.constructor(
                        OrderListResponseDto.class,
                        salesOrder.soDate,
                        orderItems.salesOrder.sorefItem,
                        product.bigImage,
                        product.prefItem,
                        product.pname,
                        product.description,
                        orderItems.obuyPrice,
                        orderItems.salesSplAmt,
                        orderItems.salesTaxAmt,
                        orderItems.delivery,
                        orderItems.oqty,
                        orderItems.delFlag
                ))
                .from(orderItems)
                .where(orderItems.orefItem.in(paginatedIds))
                .fetch();
    }
}
