package ua.mike.ecommerce.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ua.mike.ecommerce.persistence.entity.Category;
import ua.mike.ecommerce.persistence.entity.Product;
import ua.mike.ecommerce.persistence.repository.CategoryRepository;
import ua.mike.ecommerce.persistence.repository.ProductRepository;
import ua.mike.ecommerce.web.exception.NotFoundException;
import ua.mike.ecommerce.web.mapper.ProductCategoryMapper;
import ua.mike.ecommerce.web.mapper.ProductMapper;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks ProductService productService;

    @Mock CategoryRepository categoryRepository;
    @Mock ProductRepository productRepository;

    @Spy ProductCategoryMapper productCategoryMapper = Mappers.getMapper(ProductCategoryMapper.class);
    @Spy ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private static final long PRODUCT_ID = 6L;
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_URL = "product_url";
    private static final long CATEGORY_ID = 3L;
    private static final String CATEGORY_NAME = "category_name";

    @Test
    void getProductCategories() {
        var category = category();
        when(categoryRepository.findAll()).thenReturn(singletonList(category));

        var result = productService.getProductCategories();

        assertThat(result).hasSize(1);
        assertThat(result.stream().findFirst().get().id()).isEqualTo(CATEGORY_ID);
        assertThat(result.stream().findFirst().get().name()).isEqualTo(CATEGORY_NAME);

        verify(productCategoryMapper).convert(category);
    }

    @Test
    void getProducts() {
        int page = 1;
        int size = 3;
        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(products(page, size));

        var result = productService.getProducts(null, null, page, size);

        assertThat(result).hasSize(1);
        assertThat(result.getContent().getFirst().getId()).isEqualTo(PRODUCT_ID);
        assertThat(result.getContent().getFirst().getName()).isEqualTo(PRODUCT_NAME);
        assertThat(result.getContent().getFirst().getImageUrl()).isEqualTo(PRODUCT_URL);

        verify(productMapper).convert(any(Product.class));
    }

    @Test
    void getProduct() {
        var product = product();
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

        var result = productService.getProduct(PRODUCT_ID);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(PRODUCT_ID);
        assertThat(result.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(result.getImageUrl()).isEqualTo(PRODUCT_URL);

        verify(productMapper).convert(product);
    }

    @Test
    void getProduct_NotFound() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProduct(PRODUCT_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Resource with id [")
                .hasMessageContaining("] not found");
    }

    private Category category() {
        return Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .build();
    }

    private Page<Product> products(int page, int size) {
        var list = singletonList(product());
        return new PageImpl<>(list, PageRequest.of(page, size), list.size());
    }

    private Product product() {
        return Product.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .imageUrl(PRODUCT_URL)
                .build();
    }
}