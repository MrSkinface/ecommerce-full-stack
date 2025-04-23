package ua.mike.ecommerce.web.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.mike.ecommerce.services.ProductService;
import ua.mike.ecommerce.web.dto.ProductCategoryDto;
import ua.mike.ecommerce.web.dto.ProductDto;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.mike.ecommerce.Constants.Version.V1;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks ProductController productController;

    @Mock ProductService productService;

    private static final String URL = V1 + "products";

    private static final long PRODUCT_ID = 6L;
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_URL = "product_url";
    private static final long CATEGORY_ID = 3L;
    private static final String CATEGORY_NAME = "category_name";

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    @SneakyThrows
    void getProductCategories() {
        when(productService.getProductCategories()).thenReturn(singleton(category()));
        mvc.perform(get(URL + "/categories")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(CATEGORY_ID))
                .andExpect(jsonPath("$[0].name").value(CATEGORY_NAME));
    }

    @Test
    @SneakyThrows
    void getAllProducts() {
        int page = 0;
        int size = 5;
        when(productService.getProducts(null, null, page, size)).thenReturn(products(page, size));
        mvc.perform(get(URL + "?page={page}&size={size}", page, size)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(PRODUCT_ID))
                .andExpect(jsonPath("$.content[0].name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.content[0].imageUrl").value(PRODUCT_URL));
    }

    @Test
    @SneakyThrows
    void getProductsByCategory() {
        int page = 0;
        int size = 5;
        when(productService.getProducts(CATEGORY_ID, null, page, size)).thenReturn(products(page, size));
        mvc.perform(get(URL + "?category_id={categoryId}&page={page}&size={size}", CATEGORY_ID, page, size)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(PRODUCT_ID))
                .andExpect(jsonPath("$.content[0].name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.content[0].imageUrl").value(PRODUCT_URL));
    }

    @Test
    @SneakyThrows
    void getProductsByProductName() {
        int page = 0;
        int size = 5;
        when(productService.getProducts(null, PRODUCT_NAME, page, size)).thenReturn(products(page, size));
        mvc.perform(get(URL + "?name={productName}&page={page}&size={size}", PRODUCT_NAME, page, size)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(PRODUCT_ID))
                .andExpect(jsonPath("$.content[0].name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.content[0].imageUrl").value(PRODUCT_URL));
    }

    @Test
    @SneakyThrows
    void getProduct() {
        when(productService.getProduct(PRODUCT_ID)).thenReturn(product());
        mvc.perform(get(URL + "/{productId}", PRODUCT_ID)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(PRODUCT_ID))
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.imageUrl").value(PRODUCT_URL));
    }

    private ProductCategoryDto category() {
        return ProductCategoryDto.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .build();
    }

    private Page<ProductDto> products(int page, int size) {
        var list = singletonList(product());
        return new PageImpl<>(list, PageRequest.of(page, size), list.size());
    }

    private ProductDto product() {
        return ProductDto.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .imageUrl(PRODUCT_URL)
                .build();
    }
}