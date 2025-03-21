package ua.mike.ecommerce.persistence.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import ua.mike.ecommerce.config.CacheNames;
import ua.mike.ecommerce.persistence.entity.Product;

import java.util.Optional;

@CacheConfig(cacheNames = CacheNames.PRODUCTS)
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Override @NonNull @Cacheable
    Optional<Product> findById(@NonNull Long categoryId);
}
