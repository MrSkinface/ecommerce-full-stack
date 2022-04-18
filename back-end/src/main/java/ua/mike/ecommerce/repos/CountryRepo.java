package ua.mike.ecommerce.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ua.mike.ecommerce.models.Country;

@RepositoryRestResource(path = "countries", collectionResourceRel = "countries")
public interface CountryRepo extends JpaRepository<Country, Long> {
}
