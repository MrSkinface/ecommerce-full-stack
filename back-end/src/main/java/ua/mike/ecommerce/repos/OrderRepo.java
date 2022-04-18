package ua.mike.ecommerce.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mike.ecommerce.models.Order;

@RepositoryRestResource
public interface OrderRepo extends JpaRepository<Order, Long> {

    Page<Order> findByCustomerId(@RequestParam("id") long id, Pageable pageable);
}
