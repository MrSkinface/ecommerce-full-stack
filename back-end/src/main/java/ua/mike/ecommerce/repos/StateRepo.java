package ua.mike.ecommerce.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mike.ecommerce.models.State;

import java.util.Set;

@CrossOrigin
@RepositoryRestResource(path = "states", collectionResourceRel = "states")
public interface StateRepo extends JpaRepository<State, Long> {

    Set<State> findByCountryId(@RequestParam("id") long id);
}
