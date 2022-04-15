package ua.mike.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        if (this.id > 0) {
            return this.id == customer.id;
        } else {
            return firstName.equalsIgnoreCase(customer.firstName) &&
                    lastName.equalsIgnoreCase(customer.lastName) &&
                    email.equalsIgnoreCase(customer.email);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.email);
    }
}