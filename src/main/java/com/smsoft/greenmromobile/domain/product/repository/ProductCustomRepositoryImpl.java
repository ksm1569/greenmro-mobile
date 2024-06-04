package com.smsoft.greenmromobile.domain.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smsoft.greenmromobile.domain.product.dto.*;
import com.smsoft.greenmromobile.domain.product.entity.*;
import com.smsoft.greenmromobile.global.error.ErrorCode;
import com.smsoft.greenmromobile.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JPAQueryFactory queryFactory;

    @Override
    public PagedProductResponseDto<ProductRegListResponseDto> getRegisteredProducts(Long userId, Long companyId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int startRow = (int) pageable.getOffset() + 1;
        int endRow = startRow + pageSize - 1;

        // 페이징 쿼리
        String sql = "SELECT * FROM (" +
                "  SELECT a.*, ROW_NUMBER() OVER (ORDER BY a.UPDATEDON DESC) AS row_num" +
                "  FROM (" +
                "    SELECT P.PREFITEM, P.BIGIMAGE, P.PNAME, BP.BPRICE, BP.UPDATEDON FROM PRODUCTS P" +
                "    INNER JOIN BUYERPRICES BP ON P.PREFITEM = BP.PREFITEM" +
                "    WHERE BP.UCOMPANYREF = :companyId" +
                "      AND BP.EDATE >= TO_CHAR(SYSDATE, 'YYYYMMDD')" +
                "      AND P.ISUSE = 'Y' AND P.ISUSE_2 = 'Y'" +
                "  ) a" +
                ") WHERE row_num BETWEEN :startRow AND :endRow";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("companyId", companyId)
                .addValue("startRow", startRow)
                .addValue("endRow", endRow);

        List<ProductRegListResponseDto> products = jdbcTemplate.query(
                sql,
                parameters,
                new ProductRegListResponseDtoMapper()
        );

        // 전체 제품 수 계산
        String countQuery = "SELECT COUNT(*) FROM PRODUCTS P" +
                " INNER JOIN BUYERPRICES BP ON P.PREFITEM = BP.PREFITEM" +
                " WHERE BP.UCOMPANYREF = :companyId" +
                " AND BP.EDATE >= TO_CHAR(SYSDATE, 'YYYYMMDD')" +
                " AND P.ISUSE = 'Y' AND P.ISUSE_2 = 'Y'";

        Optional<Long> totalElementsOpt = Optional.ofNullable(jdbcTemplate.queryForObject(
                countQuery,
                new MapSqlParameterSource("companyId", companyId),
                Long.class)
        );

        long currentPageNumber = pageable.getPageNumber();
        long totalElements = totalElementsOpt.orElse(0L);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isLast = endRow >= totalElements;

        return new PagedProductResponseDto<>(products, currentPageNumber, totalElements, totalPages, isLast);
    }

    @Override
    public PagedProductResponseDto<ProductUnRegListResponseDto> getUnRegisteredProducts(Long userId, Long companyId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int startRow = (int) pageable.getOffset() + 1;
        int endRow = startRow + pageSize - 1;

        // 페이징 쿼리
        String sql =
                "SELECT * FROM (" +
                "  SELECT P.PREFITEM, P.BIGIMAGE, P.PNAME, BP.BPRICE, BP.UPDATEDON," +
                "         ROW_NUMBER() OVER (ORDER BY BP.UPDATEDON DESC) AS row_num " +
                "  FROM PRODUCTS P " +
                "  JOIN PRODUCTCATEGORIES PC " +
                "    ON P.PREFITEM = PC.PREFITEM " +
                "   AND PC.USE_YN = 'Y' " +
                "  JOIN CATEGORYRELATION CR " +
                "    ON PC.CREFITEM = CR.CREFITEM " +
                "  JOIN (" +
                "    SELECT CREFITEM " +
                "    FROM CATEGORYRELATION " +
                "    WHERE CPARENTREF IN (1, 4686, 5071) " +
                "    CONNECT BY PRIOR CREFITEM = CPARENTREF " +
                "    START WITH CPARENTREF IN (1, 4686, 5071) " +
                "  ) CR_HIERARCHY " +
                "    ON CR.CREFITEM = CR_HIERARCHY.CREFITEM " +
                "  JOIN BUYERPRICES BP " +
                "    ON P.PREFITEM = BP.PREFITEM " +
                "   AND BP.EDATE >= TO_CHAR(SYSDATE, 'YYYYMMDD') " +
                "  LEFT JOIN BUYERPRICES MYBP " +
                "    ON P.PREFITEM = MYBP.PREFITEM " +
                "   AND MYBP.UCOMPANYREF = :companyId " +
                "  WHERE P.ISUSE = 'Y' " +
                "    AND P.ISUSE_2 = 'Y' " +
                "    AND MYBP.PREFITEM IS NULL " +
                ") WHERE row_num BETWEEN :startRow AND :endRow";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("companyId", companyId)
                .addValue("startRow", startRow)
                .addValue("endRow", endRow);

        List<ProductUnRegListResponseDto> products = jdbcTemplate.query(
                sql,
                parameters,
                new ProductUnRegListResponseDtoMapper()
        );

        // 전체 제품 수 계산
        String countQuery =
                "  SELECT count(*)" +
                "  FROM PRODUCTS P " +
                "  JOIN PRODUCTCATEGORIES PC " +
                "    ON P.PREFITEM = PC.PREFITEM " +
                "   AND PC.USE_YN = 'Y' " +
                "  JOIN CATEGORYRELATION CR " +
                "    ON PC.CREFITEM = CR.CREFITEM " +
                "  JOIN (" +
                "    SELECT CREFITEM " +
                "    FROM CATEGORYRELATION " +
                "    WHERE CPARENTREF IN (1, 4686, 5071) " +
                "    CONNECT BY PRIOR CREFITEM = CPARENTREF " +
                "    START WITH CPARENTREF IN (1, 4686, 5071) " +
                "  ) CR_HIERARCHY " +
                "    ON CR.CREFITEM = CR_HIERARCHY.CREFITEM " +
                "  JOIN BUYERPRICES BP " +
                "    ON P.PREFITEM = BP.PREFITEM " +
                "   AND BP.EDATE >= TO_CHAR(SYSDATE, 'YYYYMMDD') " +
                "  LEFT JOIN BUYERPRICES MYBP " +
                "    ON P.PREFITEM = MYBP.PREFITEM " +
                "   AND MYBP.UCOMPANYREF = :companyId " +
                "  WHERE P.ISUSE = 'Y' " +
                "    AND P.ISUSE_2 = 'Y' " +
                "    AND MYBP.PREFITEM IS NULL ";

        Optional<Long> totalElementsOpt = Optional.ofNullable(jdbcTemplate.queryForObject(
                countQuery,
                new MapSqlParameterSource("companyId", companyId),
                Long.class)
        );

        long currentPageNumber = pageable.getPageNumber();
        long totalElements = totalElementsOpt.orElse(0L);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isLast = endRow >= totalElements;

        return new PagedProductResponseDto<>(products, currentPageNumber, totalElements, totalPages, isLast);
    }

    @Override
    public PagedProductResponseDto<ProductPopListResponseDto> getPopProducts(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int startRow = (int) pageable.getOffset() + 1;
        int endRow = startRow + pageSize - 1;

        QPopularProduct popularProduct = QPopularProduct.popularProduct;
        List<ProductPopListResponseDto> results = queryFactory
                .select(Projections.constructor(
                        ProductPopListResponseDto.class,
                        popularProduct.prefItem,
                        popularProduct.bigImage,
                        popularProduct.pname,
                        popularProduct.bprice,
                        popularProduct.cnt,
                        popularProduct.ranking
                    )
                )
                .from(popularProduct)
                .where(popularProduct.ranking.between(startRow, endRow))
                .orderBy(popularProduct.ranking.asc())
                .fetch();

        results.forEach(prod -> prod.setBigImage(formatImageUrl(prod.getBigImage())));

        long totalElements = Optional.ofNullable(queryFactory
                        .select(popularProduct.count())
                        .from(popularProduct)
                        .fetchOne())
                .orElse(0L);

        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isLast = startRow + results.size() - 1 == totalElements || results.isEmpty();

        return new PagedProductResponseDto<>(
                results,
                pageable.getPageNumber(),
                totalElements,
                totalPages,
                isLast
        );
    }

    @Override
    public ProductDetailResponseDto getProductDetail(Long companyId, Long prefItem) {
        QProduct product = QProduct.product;
        QProductContent productContent = QProductContent.productContent;
        QBuyerPrice buyerPrice = QBuyerPrice.buyerPrice;
        QVendorMasterInfo vendorMasterInfo = QVendorMasterInfo.vendorMasterInfo;

        // 최대 가격을 조회하는 서브 쿼리
        JPQLQuery<BigDecimal> maxPriceSubQuery = JPAExpressions
                .select(buyerPrice.bprice.max())
                .from(buyerPrice)
                .where(buyerPrice.product.prefItem.eq(prefItem),
                        buyerPrice.eDate.goe(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)));

        List<ProductDetailResponseDto> results = queryFactory
                .select(Projections.constructor(
                        ProductDetailResponseDto.class,
                        product.manufactureId,
                        product.manufacturer,
                        product.bigImage,
                        product.bigImageSub1,
                        product.bigImageSub2,
                        product.bigImageSub3,
                        productContent.contents,
                        product.prefItem,
                        product.pname,
                        product.description,
                        buyerPrice.bplRefItem,
                        buyerPrice.bprice.coalesce(maxPriceSubQuery).as("bprice"),
                        vendorMasterInfo.delChargeYn,
                        vendorMasterInfo.delCharge,
                        vendorMasterInfo.doseoDelChargeYn,
                        vendorMasterInfo.doseoDelCharge,
                        product.stockQty,
                        buyerPrice.minQty
                ))
                .from(product)
                .innerJoin(productContent).on(product.prefItem.eq(productContent.prefItem))
                .leftJoin(buyerPrice).on(product.prefItem.eq(buyerPrice.product.prefItem)
                        .and(buyerPrice.uCompanyRef.eq(companyId))
                        .and(buyerPrice.eDate.goe(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE))))
                .leftJoin(buyerPrice.vendorMasterInfo, vendorMasterInfo)
                .where(product.prefItem.eq(prefItem))
                .fetch();


        if (results.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_PRODUCT);
        }

        // todo 이미지 서버 이전 시 제거
        // todo 오라클 버전업 후 쿼리문 수정필요
        ProductDetailResponseDto responseDto = results.get(0);
        responseDto.setBigImage(formatImageUrl(responseDto.getBigImage()));
        responseDto.setBigImageSub1(formatImageUrl(responseDto.getBigImageSub1()));
        responseDto.setBigImageSub2(formatImageUrl(responseDto.getBigImageSub2()));
        responseDto.setBigImageSub3(formatImageUrl(responseDto.getBigImageSub3()));

        // todo 상세 이미지 - 이미지 서버 이전 시 내부 데이터 경로 수정 후 제거
        if (responseDto.getContents() != null) {
            responseDto.setContents(updateImagePaths(responseDto.getContents()));
        }

        return responseDto;
    }

    private static class ProductRegListResponseDtoMapper implements RowMapper<ProductRegListResponseDto> {
        @Override
        public ProductRegListResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductRegListResponseDto(
                    rs.getLong("PREFITEM"),
                    formatImageUrl(rs.getString("BIGIMAGE")),
                    rs.getString("PNAME"),
                    rs.getBigDecimal("BPRICE")
            );
        }
    }

    private static class ProductUnRegListResponseDtoMapper implements RowMapper<ProductUnRegListResponseDto> {
        @Override
        public ProductUnRegListResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductUnRegListResponseDto(
                    rs.getLong("PREFITEM"),
                    formatImageUrl(rs.getString("BIGIMAGE")),
                    rs.getString("PNAME"),
                    rs.getBigDecimal("BPRICE")
            );
        }
    }

    private static String formatImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.contains("https://") && !imageUrl.contains("http://")) {
            return "https://shop.greenproduct.co.kr" + imageUrl;
        }
        return imageUrl;
    }

    private static String updateImagePaths(String htmlContent) {
        String domain = "https://shop.greenproduct.co.kr";
        Pattern imgPattern = Pattern.compile("<img[^>]+src=\"(?!http)([^\"]+)\"", Pattern.CASE_INSENSITIVE);
        Matcher matcher = imgPattern.matcher(htmlContent);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String src = matcher.group(1);
            String correctedSrc = domain + src;
            matcher.appendReplacement(sb, matcher.group(0).replace(src, correctedSrc));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
