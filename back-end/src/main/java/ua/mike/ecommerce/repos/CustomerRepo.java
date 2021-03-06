package ua.mike.ecommerce.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ua.mike.ecommerce.models.Customer;

import java.util.Optional;

@RepositoryRestResource
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customer WHERE email = :email", nativeQuery = true)
    Optional<Customer> byEmail(@Param("email") String email);
}
