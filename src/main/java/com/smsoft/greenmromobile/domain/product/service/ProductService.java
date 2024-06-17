package com.smsoft.greenmromobile.domain.product.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductCustomRepository productCustomRepository;
    private final ElasticsearchClient elasticsearchClient;

    public ElasticProductSearchResponseDto productSearch(String index, String queryText, Integer size) throws IOException {
        String[] keywords = queryText.split("\\s+");
        String queryPattern;

        if (keywords.length > 1) {
            queryPattern = Arrays.stream(keywords)
                    .map(keyword -> "*" + keyword + "*")
                    .collect(Collectors.joining(" OR "));
        } else {
            queryPattern = "*" + queryText + "*";
        }

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

        SearchResponse<Object> response = elasticsearchClient.search(request, Object.class);
        log.info("response: {}", response.toString());

        List<ElasticProductResponseDto> products = response.hits().hits().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        ElasticProductSearchResponseDto searchResponseDto = new ElasticProductSearchResponseDto();
        searchResponseDto.setProducts(products);
        searchResponseDto.setCurrentPage(1);
        searchResponseDto.setTotalItems((int) response.hits().total().value());
        searchResponseDto.setTotalPages(calculateTotalPages(response.hits().total().value(), size));

        return searchResponseDto;
    }

    private ElasticProductResponseDto mapToDto(Hit<Object> hit) {
        Map<String, Object> source = (Map<String, Object>) hit.source();
        ElasticProductResponseDto dto = new ElasticProductResponseDto();
        dto.setCrefitem((Integer) source.get("crefitem"));
        dto.setPrefitem((Integer) source.get("prefitem"));
        dto.setPname((String) source.get("pname"));
        dto.setPtechdescription((String) source.get("ptechdescription"));

        return dto;
    }

    private int calculateTotalPages(long totalItems, Integer size) {
        if (size==0) size = 1;
        return (int) Math.ceil((double) totalItems / size);
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
