package ua.mike.ecommerce.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.mike.ecommerce.persistence.entity.State;

import java.util.Set;

public interface StateRepository extends JpaRepository<State, Long> {

    Set<State> findAllByCountryId(long countryId);
}
