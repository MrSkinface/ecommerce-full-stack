package ua.mike.ecommerce.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.mike.ecommerce.persistence.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customer WHERE email = :email", nativeQuery = true)
    Optional<Customer> byEmail(String email);
}
