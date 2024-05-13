package com.smsoft.greenmromobile.domain.cart.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smsoft.greenmromobile.domain.cart.dto.CartListRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.CartListResponseDto;
import com.smsoft.greenmromobile.domain.cart.entity.QCart;
import com.smsoft.greenmromobile.domain.product.entity.QBuyerPrice;
import com.smsoft.greenmromobile.domain.product.entity.QProduct;
import com.smsoft.greenmromobile.domain.product.entity.QVendorMasterInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CartCustomRepositoryImpl implements CartCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CartListResponseDto> findCartInfoByUserId(Long userId, CartListRequestDto cartListRequestDto, Pageable pageable) {
        QCart cart = QCart.cart;
        QBuyerPrice buyerPrice = QBuyerPrice.buyerPrice;
        QProduct product = QProduct.product;
        QVendorMasterInfo vendorMasterInfo = QVendorMasterInfo.vendorMasterInfo;

        // 필터
        BooleanBuilder filter = new BooleanBuilder();
        filter.and(cart.uRefItem.eq(userId));

        // 메인쿼리
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        List<Tuple> tupleList = queryFactory
                .select(cart.buyerPrice.bplRefItem, cart.ciRefItem)
                .from(cart)
                .innerJoin(cart.buyerPrice, buyerPrice)
                .on(cart.buyerPrice.bplRefItem.eq(buyerPrice.bplRefItem)
                        .and(buyerPrice.eDate.goe(today)))
                .innerJoin(cart.product, product)
                .on(cart.product.prefItem.eq(product.prefItem))
                .innerJoin(buyerPrice.vendorMasterInfo, vendorMasterInfo)
                .on(buyerPrice.vendorMasterInfo.vendorRefItem.eq(vendorMasterInfo.vendorRefItem))
                .where(cart.uRefItem.eq(userId))
                .orderBy(product.manufacturer.asc(), cart.addedOn.desc())
                .fetch();

        for (Tuple tuple : tupleList) {
            tuple.get(cart.buyerPrice.bplRefItem);
            tuple.get(cart.ciRefItem);
        }



        return null;
    }
}
