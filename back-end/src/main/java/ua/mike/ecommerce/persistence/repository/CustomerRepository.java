package ua.mike.ecommerce.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.mike.ecommerce.persistence.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
}
