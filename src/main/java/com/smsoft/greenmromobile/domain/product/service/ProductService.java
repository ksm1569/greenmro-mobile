package com.smsoft.greenmromobile.domain.product.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smsoft.greenmromobile.domain.product.dto.*;
import com.smsoft.greenmromobile.domain.product.repository.ProductCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final JPAQueryFactory queryFactory;
    private final ProductCustomRepository productCustomRepository;
    private final ElasticsearchClient elasticsearchClient;

    public PagedProductResponseDto<ProductsByCategoryResponseDto> getProductsByCategory(ProductsByCategoryRequestDto requestDto) {
        log.info("categoryId : {}, page : {}, size : {}", requestDto.getCategoryId(), requestDto.getPage(), requestDto.getSize());

        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());
        return productCustomRepository.getProductsByCategory(requestDto.getCategoryId(), requestDto.getSort(), pageable);
    }

    public ElasticProductSearchResponseDto productRelatedList(Long ucompanyRef, String index, String queryText, String pinnedId, Integer size, Integer page, String sort) throws IOException {
        String queryPattern = getProductQueryPattern(queryText);
        int fromIndex = (page - 1) * size;
        log.info("Performing search on index: {} with queryText: {} and queryPattern: {} and size: {}", index, queryText, queryPattern, size);

        SearchRequest.Builder builder = new SearchRequest.Builder()
                .index(index)
                .from(fromIndex)
                .size(size)
                .query(q -> q
                        .pinned(p -> p
                                .ids(Collections.singletonList(pinnedId))
                                .organic(
                                        Query.of(qb -> qb
                                                .bool(b -> b
                                                        .must(mu -> mu
                                                                .queryString(qs -> qs
                                                                        .defaultField("ptechdescription")
                                                                        .query(queryPattern)
                                                                        .analyzeWildcard(true)
                                                                )
                                                        )
                                                        .filter(f -> f
                                                                .term(t -> t
                                                                        .field("isuse.keyword")
                                                                        .value("Y")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );

        if (sort != null) {
            builder.query(q -> q
                    .bool(b -> b
                            .must(mu -> mu
                                    .queryString(qs -> qs
                                            .defaultField("ptechdescription")
                                            .query(queryPattern)
                                            .analyzeWildcard(true)
                                    )
                            )
                            .mustNot(mn -> mn
                                    .term(t -> t
                                            .field("price")
                                            .value(-1)
                                    )
                            )
                            .filter(f -> f
                                    .term(t -> t
                                            .field("isuse.keyword")
                                            .value("Y")
                                    )
                            )
                    )
            );

            switch (sort) {
                case "price_asc":
                    builder.sort(s -> s
                            .field(f -> f
                                    .field("price")
                                    .order(SortOrder.Asc)
                            )
                    );
                    break;
                case "price_desc":
                    builder.sort(s -> s
                            .field(f -> f
                                    .field("price")
                                    .order(SortOrder.Desc)
                            )
                    );
                    break;
            }
        }


        SearchRequest request = builder.build();

        //responseDto.getProducts().forEach(product -> addPriceToProduct(product, ucompanyRef));
        return getElasticProductSearchResponseDto(queryText, size, page, request);
    }

    public ElasticProductSearchResponseDto productSearch(String index, String queryText, Integer size) throws IOException {
        String queryPattern = getProductQueryPattern(queryText);
        log.info("Performing search on index: {} with queryText: {} and queryPattern: {} and size: {}", index, queryText, queryPattern, size);

        SearchRequest request = SearchRequest.of(s -> s
                .index(index)
                .query(q -> q
                        .bool(b -> b
                                .must(mu -> mu
                                        .queryString(qs -> qs
                                                .defaultField("ptechdescription")
                                                .query(queryPattern)
                                                .analyzeWildcard(true)
                                        )
                                )
                                .filter(f -> f
                                        .term(t -> t
                                                .field("isuse.keyword")
                                                .value("Y")
                                        )
                                )
                        )
                )
                .size(size)
        );

        return getElasticProductSearchResponseDto(queryText, size, 1, request);
    }

//    private void addPriceToProduct(ElasticProductResponseDto product, Long ucompanyRef) {
//        Long prefItem = Long.valueOf(product.getPrefitem());
//        LocalDate today = LocalDate.now();
//
//        QBuyerPrice buyerPrice = QBuyerPrice.buyerPrice;
//
//        // 1. 해당 고객사 가격
//        BigDecimal price = queryFactory
//                .select(buyerPrice.bprice)
//                .from(buyerPrice)
//                .where(buyerPrice.product.prefItem.eq(prefItem),
//                        buyerPrice.uCompanyRef.eq(ucompanyRef),
//                        buyerPrice.eDate.goe(today.format(DateTimeFormatter.BASIC_ISO_DATE)))
//                .fetchOne();
//
//        // 2. 전체 고객사의 최대 가격
//        if (price == null) {
//            price = queryFactory
//                    .select(buyerPrice.bprice.max())
//                    .from(buyerPrice)
//                    .where(buyerPrice.product.prefItem.eq(prefItem),
//                            buyerPrice.eDate.goe(today.format(DateTimeFormatter.BASIC_ISO_DATE)))
//                    .fetchOne();
//        }
//
//        product.setPrice(Optional.ofNullable(price).orElse(BigDecimal.valueOf(-1)));
//    }

    private ElasticProductSearchResponseDto getElasticProductSearchResponseDto(String queryText, Integer size, Integer page, SearchRequest request) throws IOException {
        SearchResponse<Object> response = elasticsearchClient.search(request, Object.class);
        log.info("response: {}", response.toString());

        List<ElasticProductResponseDto> products = response.hits().hits().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        ElasticProductSearchResponseDto searchResponseDto = new ElasticProductSearchResponseDto();
        searchResponseDto.setProducts(products);
        searchResponseDto.setCurrentPage(page);
        searchResponseDto.setTotalItems((int) response.hits().total().value());
        searchResponseDto.setTotalPages(calculateTotalPages(response.hits().total().value(), size));
        searchResponseDto.setQueryText(queryText);

        return searchResponseDto;
    }

    private static String getProductQueryPattern(String queryText) {
        String[] keywords = queryText.split("\\s+");
        String queryPattern;

        if (keywords.length > 1) {
            queryPattern = Arrays.stream(keywords)
                    .map(keyword -> "*" + keyword + "*")
                    .collect(Collectors.joining(" OR "));
        } else {
            queryPattern = "*" + queryText + "*";
        }
        return queryPattern;
    }

    private ElasticProductResponseDto mapToDto(Hit<Object> hit) {
        Map<String, Object> source = (Map<String, Object>) hit.source();
        ElasticProductResponseDto dto = new ElasticProductResponseDto();
        dto.setCrefitem((Integer) source.get("crefitem"));
        dto.setPrefitem((Integer) source.get("prefitem"));
        dto.setPname((String) source.get("pname"));
        dto.setPtechdescription((String) source.get("ptechdescription"));
        dto.setBigImage(formatImageUrl((String) source.get("bigimage")));
        dto.setPrice((Integer) source.get("price"));

        return dto;
    }

    private int calculateTotalPages(long totalItems, Integer size) {
        if (size==0) size = 1;
        return (int) Math.ceil((double) totalItems / size);
    }

    private String formatImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.contains("https://") && !imageUrl.contains("http://")) {
            return "https://shop.greenproduct.co.kr" + imageUrl;
        }
        return imageUrl;
    }

    @Transactional(readOnly = true)
    public PagedProductResponseDto<ProductRegListResponseDto> getRegisteredProducts(
        Long userId,
        Long companyId,
        ProductRegListRequestDto productRegListRequestDto
    ) {
        Pageable pageable = PageRequest.of(productRegListRequestDto.page(), productRegListRequestDto.size() + 2);

        return productCustomRepository.getRegisteredProducts(userId, companyId, pageable);
    }

    @Transactional(readOnly = true)
    public PagedProductResponseDto<ProductUnRegListResponseDto> getUnRegisteredProducts(
            Long userId,
            Long companyId,
            ProductUnRegListRequestDto productUnRegListRequestDto
    ) {
        Pageable pageable = PageRequest.of(productUnRegListRequestDto.page(), productUnRegListRequestDto.size() + 2);

        return productCustomRepository.getUnRegisteredProducts(userId, companyId, pageable);
    }

    @Transactional(readOnly = true)
    public PagedProductResponseDto<ProductNewListResponseDto> getNewProducts(
            ProductNewRequestDto productNewRequestDto
    ) {
        Pageable pageable = PageRequest.of(productNewRequestDto.page(), productNewRequestDto.size());

        return productCustomRepository.getNewProducts(pageable);
    }

    @Transactional(readOnly = true)
    public PagedProductResponseDto<ProductPopListResponseDto> getPopProducts(
            ProductPopListRequestDto productPopListRequestDto
    ) {
        Pageable pageable = PageRequest.of(productPopListRequestDto.page(), productPopListRequestDto.size());

        return productCustomRepository.getPopProducts(pageable);
    }

    @Transactional(readOnly = true)
    public ProductDetailResponseDto getProductDetail(Long companyId, Long prefItem) {
        return productCustomRepository.getProductDetail(companyId, prefItem);
    }
}
