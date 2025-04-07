package com.microservices.catalog_service.domain;

import com.microservices.catalog_service.ApplicationProperties;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationProperties applicationProperties;

    ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties) {
        this.productRepository = productRepository;
        this.applicationProperties = applicationProperties;
    }

    public PagedResult<Product> getProducts(int pageNo) {

        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = (Pageable) PageRequest.of(pageNo, applicationProperties.pageSize(), sort);
        Page<Product> productsPage = productRepository.findAll(pageable).map(ProductMapper::toEntity);
        return new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1, // BECAUSE JPA'S 1ST PAGE IS PAGE NO. 0
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasPrevious(),
                productsPage.hasNext());
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code).map(ProductMapper::toEntity);
    }
}
