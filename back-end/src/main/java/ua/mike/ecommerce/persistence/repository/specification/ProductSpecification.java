package ua.mike.ecommerce.persistence.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ua.mike.ecommerce.persistence.entity.Product;

import static org.springframework.data.jpa.domain.Specification.where;

public class ProductSpecification {

    private ProductSpecification() { }

    public static ProductSpecificationBuilder builder() {
        return new ProductSpecificationBuilder();
    }

    public static class ProductSpecificationBuilder {

        private Long categoryId;
        private String name;

        private ProductSpecificationBuilder() { }

        public ProductSpecificationBuilder category(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public ProductSpecificationBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Specification<Product> build() {
            Specification<Product> specification = where(null);
            if (categoryId != null) {
                specification = specification.and((product, cq, cb) ->
                        cb.equal(product.get("category").get("id"), categoryId));
            }
            if (StringUtils.hasText(name)) {
                specification = specification.and((product, cq, cb) ->
                        cb.like(cb.upper(product.get("name")), "%" + name.toUpperCase() + "%"));
            }
            return specification;
        }
    }
}
