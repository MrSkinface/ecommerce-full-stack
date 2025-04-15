package ua.mike.ecommerce.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.mike.ecommerce.persistence.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}