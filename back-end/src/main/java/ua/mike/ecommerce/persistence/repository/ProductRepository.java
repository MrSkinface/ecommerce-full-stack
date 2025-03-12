package ua.mike.ecommerce.persistence.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ua.mike.ecommerce.persistence.entity.Product;

import java.util.Optional;

@CacheConfig(cacheNames = "product")
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override @NonNull @Cacheable
    Optional<Product> findById(@NonNull Long categoryId);

    Page<Product> findAllByCategoryId(long categoryId, Pageable pageable);

    Page<Product> findAllByNameContainingIgnoreCase(String productName, Pageable pageable);

}
