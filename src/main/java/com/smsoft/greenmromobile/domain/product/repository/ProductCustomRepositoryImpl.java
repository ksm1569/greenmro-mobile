package com.smsoft.greenmromobile.domain.product.repository;

import com.smsoft.greenmromobile.domain.product.dto.PagedProductRegResponseDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductRegListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public PagedProductRegResponseDto<ProductRegListResponseDto> getRegisteredProducts(Long userId, Long companyId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int startRow = (int) pageable.getOffset() + 1;
        int endRow = startRow + pageSize - 1;

        // 페이징 쿼리
        String sql = "SELECT * FROM (" +
                "  SELECT a.*, ROW_NUMBER() OVER (ORDER BY a.UPDATEDON DESC) AS row_num" +
                "  FROM (" +
                "    SELECT P.BIGIMAGE, P.PNAME, BP.BPRICE, BP.UPDATEDON FROM PRODUCTS P" +
                "    INNER JOIN BUYERPRICES BP ON P.PREFITEM = BP.PREFITEM" +
                "    WHERE BP.UCOMPANYREF = ?" +
                "      AND BP.EDATE >= TO_CHAR(SYSDATE, 'YYYYMMDD')" +
                "      AND P.ISUSE = 'Y' AND P.ISUSE_2 = 'Y'" +
                "  ) a" +
                ") WHERE row_num BETWEEN ? AND ?";

        List<ProductRegListResponseDto> products = jdbcTemplate.query(
                sql,
                ps -> {
                    ps.setLong(1, companyId);
                    ps.setInt(2, startRow);
                    ps.setInt(3, endRow);
                },
                new ProductRegListResponseDtoMapper());

        // 전체 제품 수 계산
        String countQuery = "SELECT COUNT(*) FROM PRODUCTS P" +
                " INNER JOIN BUYERPRICES BP ON P.PREFITEM = BP.PREFITEM" +
                " WHERE BP.UCOMPANYREF = ?" +
                " AND BP.EDATE >= TO_CHAR(SYSDATE, 'YYYYMMDD')" +
                " AND P.ISUSE = 'Y' AND P.ISUSE_2 = 'Y'";

        Optional<Long> totalElementsOpt = Optional.ofNullable(
                jdbcTemplate.queryForObject(countQuery, Long.class, companyId)
        );

        Long totalElements = totalElementsOpt.orElse(0L);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isLast = endRow >= totalElements;

        return new PagedProductRegResponseDto<>(products, totalElements, totalPages, isLast);
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

    private static String formatImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.contains("https://")) {
            return "https://shop.greenproduct.co.kr" + imageUrl;
        }
        return imageUrl;
    }
}
