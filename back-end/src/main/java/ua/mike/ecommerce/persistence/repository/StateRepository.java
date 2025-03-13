package ua.mike.ecommerce.persistence.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.mike.ecommerce.config.CacheNames;
import ua.mike.ecommerce.persistence.entity.State;

import java.util.Set;

@CacheConfig(cacheNames = CacheNames.STATES)
public interface StateRepository extends JpaRepository<State, Long> {

    @Cacheable
    Set<State> findAllByCountryId(long countryId);
}
