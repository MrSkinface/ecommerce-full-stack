package ua.mike.ecommerce.web.controllers;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.mike.ecommerce.services.ProductService;
import ua.mike.ecommerce.web.dto.ProductCategoryDto;
import ua.mike.ecommerce.web.dto.ProductDto;

import java.util.Set;

import static ua.mike.ecommerce.Constants.Version.V1;

@RestController
@RequestMapping(V1 + "products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("categories")
    public Set<ProductCategoryDto> getProductCategories() {
        return productService.getProductCategories();
    }

    @GetMapping
    public Page<ProductDto> getProducts(@RequestParam(name = "category_id", required = false) Long categoryId,
                                        @RequestParam(name = "name", required = false) String productName,
                                        @RequestParam(required = false, defaultValue = "0") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return productService.getProducts(categoryId, productName, page, size);
    }

    @GetMapping("{id}")
    public ProductDto getProduct(@PathVariable @Positive Long id) {
        return productService.getProduct(id);
    }
}
