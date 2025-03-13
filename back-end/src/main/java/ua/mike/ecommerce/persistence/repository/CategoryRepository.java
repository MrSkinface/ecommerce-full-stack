package ua.mike.ecommerce.persistence.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ua.mike.ecommerce.config.CacheNames;
import ua.mike.ecommerce.persistence.entity.Category;

import java.util.List;

@CacheConfig(cacheNames = CacheNames.CATEGORIES)
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override @NonNull @Cacheable
    List<Category> findAll();
}
