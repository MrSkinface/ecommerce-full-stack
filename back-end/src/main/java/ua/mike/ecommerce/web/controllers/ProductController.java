package ua.mike.ecommerce.web.controllers;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ua.mike.ecommerce.persistence.entity.Product;
import ua.mike.ecommerce.persistence.repository.CategoryRepository;
import ua.mike.ecommerce.persistence.repository.ProductRepository;
import ua.mike.ecommerce.web.dto.ProductCategoryDto;
import ua.mike.ecommerce.web.dto.ProductDto;
import ua.mike.ecommerce.web.exception.NotFoundException;
import ua.mike.ecommerce.web.mapper.ProductCategoryMapper;
import ua.mike.ecommerce.web.mapper.ProductMapper;

import java.util.Set;
import java.util.stream.Collectors;

import static ua.mike.ecommerce.Constants.API_VERSION;

/**
 * Created by mike on 12.04.2024 16:56
 */
@RestController
@RequestMapping(API_VERSION + "products")
@RequiredArgsConstructor
public class ProductController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final ProductCategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    @GetMapping("categories")
    public Set<ProductCategoryDto> getProductCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::convert)
                .collect(Collectors.toSet());
    }

    @GetMapping
    public Page<ProductDto> getProducts(@RequestParam(name = "category_id", required = false) Long categoryId,
                                        @RequestParam(name = "name", required = false) String productName,
                                        @RequestParam(required = false, defaultValue = "0") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        final var pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
        final Page<Product> result;
        if (categoryId != null) {
            result = productRepository.findAllByCategoryId(categoryId, pageable);
        } else if (StringUtils.hasText(productName)) {
            result = productRepository.findAllByNameContainingIgnoreCase(productName, pageable);
        } else {
            result = productRepository.findAll(pageable);
        }

        return result.map(productMapper::convert);
    }

    @GetMapping("{id}")
    public ProductDto getProduct(@PathVariable @Positive Long id) {
        return productRepository.findById(id)
                .map(productMapper::convert)
                .orElseThrow(() -> new NotFoundException("Product with id %s not found".formatted(id)));
    }
}
