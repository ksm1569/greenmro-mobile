package com.smsoft.greenmromobile.domain.cart.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smsoft.greenmromobile.domain.cart.dto.CartListRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.CartListResponseDto;
import com.smsoft.greenmromobile.domain.cart.dto.PagedCartResponseDto;
import com.smsoft.greenmromobile.domain.cart.entity.QCart;
import com.smsoft.greenmromobile.domain.product.entity.QBuyerPrice;
import com.smsoft.greenmromobile.domain.product.entity.QProduct;
import com.smsoft.greenmromobile.domain.product.entity.QVendorMasterInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CartCustomRepositoryImpl implements CartCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public PagedCartResponseDto findCartInfoByUserId(Long userId, CartListRequestDto cartListRequestDto, Pageable pageable) {
        QCart subCart = new QCart("subCart");
        QProduct subProduct = new QProduct("subProduct");

        QCart cart = QCart.cart;
        QBuyerPrice buyerPrice = QBuyerPrice.buyerPrice;
        QProduct product = QProduct.product;
        QVendorMasterInfo vendorMasterInfo = QVendorMasterInfo.vendorMasterInfo;

        // 필터
        BooleanBuilder filter = new BooleanBuilder();
        filter.and(cart.uRefItem.eq(userId));

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 전체 카운트를 계산하는 쿼리
        Long totalElementsResult = queryFactory
                .select(cart.count())
                .from(cart)
                .innerJoin(cart.buyerPrice, buyerPrice)
                .on(buyerPrice.eDate.goe(today))
                .innerJoin(cart.product, product)
                .innerJoin(buyerPrice.vendorMasterInfo, vendorMasterInfo)
                .where(filter)
                .fetchOne();



        // 메인쿼리
        List<Tuple> tupleList = queryFactory
                .select(cart.product.manufactureId,
                        cart.product.manufacturer,
                        cart.product.pname,
                        cart.product.bigImage,
                        cart.oQty,
                        cart.buyerPrice.bprice,
                        vendorMasterInfo.delChargeYn,
                        vendorMasterInfo.delCharge,
                        vendorMasterInfo.doseoDelChargeYn,
                        vendorMasterInfo.doseoDelCharge,
                        JPAExpressions
                                .select(subCart.addedOn.max())
                                .from(subCart)
                                .innerJoin(subProduct).on(subProduct.prefItem.eq(subCart.product.prefItem))
                                .where(subCart.uRefItem.eq(userId)
                                        .and(subProduct.manufactureId.eq(product.manufactureId))
                                ).groupBy(subProduct.manufactureId),
                        cart.addedOn)
                .from(cart)
                .innerJoin(cart.buyerPrice, buyerPrice)
                .on(buyerPrice.eDate.goe(today))
                .innerJoin(cart.product, product)
                .innerJoin(buyerPrice.vendorMasterInfo, vendorMasterInfo)
                .where(cart.uRefItem.eq(userId))
                .orderBy(new OrderSpecifier<>(Order.DESC, JPAExpressions
                        .select(subCart.addedOn.max())
                        .from(subCart)
                        .innerJoin(subProduct).on(subProduct.prefItem.eq(subCart.product.prefItem))
                        .where(subCart.uRefItem.eq(userId)
                                .and(subProduct.manufactureId.eq(product.manufactureId))
                        ).groupBy(subProduct.manufactureId)), cart.addedOn.desc())
                .fetch();

        long totalElements = totalElementsResult != null ? totalElementsResult : 0L;
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize()) - 1;
        boolean last = pageable.getPageNumber() >= totalPages - 1;

        // 페이징 처리
        List<CartListResponseDto> paginatedResult = tupleList.stream()
                .map(tuple -> new CartListResponseDto(
                        tuple.get(cart.product.manufactureId),
                        tuple.get(cart.product.manufacturer),
                        tuple.get(cart.product.pname),
                        tuple.get(cart.product.bigImage),
                        tuple.get(cart.oQty),
                        tuple.get(cart.buyerPrice.bprice),
                        tuple.get(vendorMasterInfo.delChargeYn),
                        tuple.get(vendorMasterInfo.delCharge),
                        tuple.get(vendorMasterInfo.doseoDelChargeYn),
                        tuple.get(vendorMasterInfo.doseoDelCharge),
                        tuple.get(JPAExpressions
                                .select(subCart.addedOn.max())
                                .from(subCart)
                                .innerJoin(subProduct).on(subProduct.prefItem.eq(subCart.product.prefItem))
                                .where(subCart.uRefItem.eq(userId)
                                        .and(subProduct.manufactureId.eq(product.manufactureId))
                                ).groupBy(subProduct.manufactureId)),
                        tuple.get(cart.addedOn)
                ))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();

        // 제조사별로 그룹화
        Map<String, List<CartListResponseDto>> groupedCartInfo = paginatedResult.stream()
                .collect(Collectors.groupingBy(CartListResponseDto::manufacturer));

        // Dto 매핑 및 리턴
        return new PagedCartResponseDto(
                groupedCartInfo,
                totalElements,
                totalPages,
                last
        );
    }
}