package ua.mike.ecommerce.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ua.mike.ecommerce.models.Product;

@CrossOrigin
public interface ProductRepo extends JpaRepository<Product, Long> {

}
