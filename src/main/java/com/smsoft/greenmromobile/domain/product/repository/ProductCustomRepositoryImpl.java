package com.smsoft.greenmromobile.domain.product.repository;

import com.smsoft.greenmromobile.domain.product.dto.PagedProductResponseDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductRegListResponseDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductUnRegListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public PagedProductResponseDto<ProductRegListResponseDto> getRegisteredProducts(Long userId, Long companyId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int startRow = (int) pageable.getOffset() + 1;
        int endRow = startRow + pageSize - 1;

        // 페이징 쿼리
        String sql = "SELECT * FROM (" +
                "  SELECT a.*, ROW_NUMBER() OVER (ORDER BY a.UPDATEDON DESC) AS row_num" +
                "  FROM (" +
                "    SELECT P.BIGIMAGE, P.PNAME, BP.BPRICE, BP.UPDATEDON FROM PRODUCTS P" +
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

    private static class ProductRegListResponseDtoMapper implements RowMapper<ProductRegListResponseDto> {
        @Override
        public ProductRegListResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductRegListResponseDto(
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
}
