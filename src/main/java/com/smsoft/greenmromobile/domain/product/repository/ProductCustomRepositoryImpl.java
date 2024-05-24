package com.smsoft.greenmromobile.domain.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
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

@RequiredArgsConstructor
@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository{
    private final JdbcTemplate jdbcTemplate;
    private final JPAQueryFactory queryFactory;

    @Override
    public PagedProductRegResponseDto<ProductRegListResponseDto> getRegisteredProducts(Long userId, Long companyId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int startRow = (int) pageable.getOffset() + 1;
        int endRow = startRow + pageSize - 1;

        // 오라클 SQL 페이징 쿼리
        String sql = "SELECT * FROM (" +
                "  SELECT a.*, ROWNUM rnum FROM (" +
                "    SELECT P.BIGIMAGE, P.PNAME, BP.BPRICE FROM PRODUCTS P" +
                "    INNER JOIN BUYERPRICES BP ON P.PREFITEM = BP.PREFITEM" +
                "    WHERE BP.UCOMPANYREF = ?" +
                "      AND BP.EDATE >= TO_CHAR(SYSDATE, 'YYYYMMDD')" +
                "      AND P.ISUSE = 'Y' AND P.ISUSE_2 = 'Y'" +
                "    ORDER BY BP.UPDATEDON DESC" +
                "  ) a WHERE ROWNUM <= ?" +  // 최대 행 수
                ") WHERE rnum >= ?";         // 최소 행 시작

        List<ProductRegListResponseDto> products = jdbcTemplate.query(
                sql,
                new Object[] { companyId, endRow, startRow },
                new ProductRegListResponseDtoMapper());

        // 전체 제품 수 계산
        String countSql = "SELECT COUNT(*) FROM PRODUCTS P" +
                " INNER JOIN BUYERPRICES BP ON P.PREFITEM = BP.PREFITEM" +
                " WHERE BP.UCOMPANYREF = ?" +
                " AND BP.EDATE >= TO_CHAR(SYSDATE, 'YYYYMMDD')" +
                " AND P.ISUSE = 'Y' AND P.ISUSE_2 = 'Y'";

        Long totalElements = jdbcTemplate.queryForObject(countSql, new Object[] { companyId }, Long.class);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isLast = endRow >= totalElements;

        return new PagedProductRegResponseDto<>(products, totalElements, totalPages, isLast);
    }

    private static class ProductRegListResponseDtoMapper implements RowMapper<ProductRegListResponseDto> {
        @Override
        public ProductRegListResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductRegListResponseDto(
                    rs.getString("BIGIMAGE"),
                    rs.getString("PNAME"),
                    rs.getBigDecimal("BPRICE")
            );
        }
    }
}
