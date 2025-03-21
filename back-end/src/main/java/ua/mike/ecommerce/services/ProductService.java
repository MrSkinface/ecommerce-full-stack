package ua.mike.ecommerce.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ua.mike.ecommerce.persistence.repository.CategoryRepository;
import ua.mike.ecommerce.persistence.repository.ProductRepository;
import ua.mike.ecommerce.persistence.repository.specification.ProductSpecification;
import ua.mike.ecommerce.web.dto.ProductCategoryDto;
import ua.mike.ecommerce.web.dto.ProductDto;
import ua.mike.ecommerce.web.exception.NotFoundException;
import ua.mike.ecommerce.web.mapper.ProductCategoryMapper;
import ua.mike.ecommerce.web.mapper.ProductMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final ProductCategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    public Set<ProductCategoryDto> getProductCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::convert)
                .collect(Collectors.toSet());
    }

    public Page<ProductDto> getProducts(@Nullable Long categoryId, @Nullable String productName, int page, int size) {
        final var pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
        final var specification = ProductSpecification.builder()
                .category(categoryId)
                .name(productName)
                .build();
        return productRepository.findAll(specification, pageable)
                .map(productMapper::convert);
    }

    public ProductDto getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::convert)
                .orElseThrow(() -> new NotFoundException(productId));
    }
}
