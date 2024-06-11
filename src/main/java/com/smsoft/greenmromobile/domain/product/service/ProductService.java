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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductCustomRepository productCustomRepository;
    private final ElasticsearchClient elasticsearchClient;

    public ElasticProductSearchResponseDto productSearch(String index, String queryText) throws IOException {
        String wildcardText = "*" + queryText + "*";
        log.info("Performing search on index: {} with query: {}", index, wildcardText);
        SearchRequest request = SearchRequest.of(s -> s
                .index(index)
                .query(q -> q
                        .queryString(qs -> qs
                                .defaultField("ptechdescription")
                                .query(wildcardText)
                        )
                )
                .size(17)
        );

        SearchResponse<Object> response = elasticsearchClient.search(request, Object.class);
        List<ElasticProductResponseDto> products = response.hits().hits().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        ElasticProductSearchResponseDto searchResponseDto = new ElasticProductSearchResponseDto();
        searchResponseDto.setProducts(products);
        searchResponseDto.setCurrentPage(1);
        searchResponseDto.setTotalItems((int) response.hits().total().value());
        searchResponseDto.setTotalPages(calculateTotalPages(response.hits().total().value()));

        return searchResponseDto;
    }

    private ElasticProductResponseDto mapToDto(Hit<Object> hit) {
        Map<String, Object> source = (Map<String, Object>) hit.source();
        ElasticProductResponseDto dto = new ElasticProductResponseDto();
        dto.setPname((String) source.get("pname"));
        dto.setPtechdescription((String) source.get("ptechdescription"));

        Object prefItemObj = source.get("prefitem");
        if (prefItemObj instanceof Integer) {
            dto.setPrefitem(((Integer) prefItemObj).longValue());  // Integer를 Long으로 변환
        } else if (prefItemObj instanceof Long) {
            dto.setPrefitem((Long) prefItemObj);
        } else {
            throw new IllegalArgumentException("Invalid type for prefitem: " + prefItemObj.getClass().getName());
        }

        return dto;
    }

    private int calculateTotalPages(long totalItems) {
        return (int) Math.ceil((double) totalItems / 17);
    }

    private ElasticProductResponseDto mapToDto(Map<String, Object> source) {
        ElasticProductResponseDto dto = new ElasticProductResponseDto();
        dto.setPrefitem((Long) source.get("prefitem"));
        dto.setPname((String) source.get("pname"));
        dto.setPtechdescription((String) source.get("ptechdescription"));
        return dto;
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
