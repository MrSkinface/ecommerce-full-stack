package ua.mike.ecommerce.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mike.ecommerce.models.Product;

@RepositoryRestResource
public interface ProductRepo extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(@RequestParam("id") long id, Pageable pageable);

    Page<Product> findByNameContaining(@RequestParam("name") String name, Pageable pageable);

}
