package ua.mike.ecommerce.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ua.mike.ecommerce.models.Customer;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customer WHERE first_name = :firstName AND last_name = :lastName AND email = :email", nativeQuery = true)
    Optional<Customer> search(@Param("firstName") String firstName,
                              @Param("lastName") String lastName,
                              @Param("email") String email);
}
