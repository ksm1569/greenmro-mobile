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
                .leftJoin(salesOrder).on(orderItems.salesOrder.sorefItem.eq(salesOrder.sorefItem))
                .leftJoin(product).on(orderItems.product.prefItem.eq(product.prefItem))
                .where(orderItems.ownerUserId.eq(userId))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
                .fetch();
    }
}
