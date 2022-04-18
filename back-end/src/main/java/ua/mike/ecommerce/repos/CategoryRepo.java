package ua.mike.ecommerce.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ua.mike.ecommerce.models.Category;

@RepositoryRestResource(path = "product-category", collectionResourceRel = "categories")
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
