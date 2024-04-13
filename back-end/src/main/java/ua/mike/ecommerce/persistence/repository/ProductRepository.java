package ua.mike.ecommerce.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.mike.ecommerce.persistence.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategoryId(long categoryId, Pageable pageable);

    Page<Product> findAllByNameContainingIgnoreCase(String productName, Pageable pageable);

}
