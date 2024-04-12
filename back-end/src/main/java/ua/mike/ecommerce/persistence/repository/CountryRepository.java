package ua.mike.ecommerce.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.mike.ecommerce.persistence.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
