package ua.mike.ecommerce.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.mike.ecommerce.persistence.entity.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByCustomerId(long customerId, Pageable pageable);

    Optional<Order> findByCustomerIdAndId(Long customerId, Long id);
}
