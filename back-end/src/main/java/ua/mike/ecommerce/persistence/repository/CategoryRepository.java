package ua.mike.ecommerce.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.mike.ecommerce.persistence.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
