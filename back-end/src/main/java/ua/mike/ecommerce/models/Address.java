package ua.mike.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
    private String street;
    @Column(name = "zip_code")
    private String zip;
    @OneToMany(mappedBy = "billingAddress")
    private Set<Order> billing = new HashSet<>();
    @OneToMany(mappedBy = "shippingAddress")
    private Set<Order> shipping = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Address address)) return false;

        if (address.id > 0 && this.id > 0)
            return address.id == this.id;
        else
            return city.equals(address.city) &&
                    ((country == null && address.country == null) || (country != null && address.country != null && country.getId() == address.country.getId())) &&
                    ((state == null && address.state == null) || (state != null && address.state != null && state.getId() == address.state.getId())) &&
                    street.equals(address.street) &&
                    zip.equals(address.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, country, state, street, zip);
    }
}