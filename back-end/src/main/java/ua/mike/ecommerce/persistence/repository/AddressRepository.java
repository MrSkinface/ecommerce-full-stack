package ua.mike.ecommerce.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.mike.ecommerce.persistence.entity.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "SELECT * FROM address WHERE country_id = :country " +
            "AND state_id = :state " +
            "AND city = :city " +
            "AND street = :street " +
            "AND zip_code = :zip", nativeQuery = true)
    Optional<Address> search(
            @Param("country") long countryId,
            @Param("state") long stateId,
            @Param("city") String city,
            @Param("street") String street,
            @Param("zip") String zip
    );
}
