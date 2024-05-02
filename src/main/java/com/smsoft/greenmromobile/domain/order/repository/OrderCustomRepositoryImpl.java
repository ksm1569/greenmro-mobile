package com.smsoft.greenmromobile.domain.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smsoft.greenmromobile.domain.order.dto.OrderSummaryDto;
import com.smsoft.greenmromobile.domain.order.entity.QOrderItems;
import com.smsoft.greenmromobile.domain.order.entity.QSalesOrder;
import com.smsoft.greenmromobile.domain.product.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderSummaryDto> findOrderSummariesByUserId(Long userId, Pageable pageable) {
        QOrderItems orderItems = QOrderItems.orderItems;
        QSalesOrder salesOrder = QSalesOrder.salesOrder;
        QProduct product = QProduct.product;
        
        List<Long> ids = queryFactory
                .select(orderItems.orefItem)
                .from(orderItems)
                .leftJoin(salesOrder).on(orderItems.salesOrder.sorefItem.eq(salesOrder.sorefItem))
                .where(orderItems.ownerUserId.eq(userId))
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
                        OrderSummaryDto.class,
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
