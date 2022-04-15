package ua.mike.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!id.equals(address.id)) return false;
        if (!city.equals(address.city)) return false;
        if (!country.equals(address.country)) return false;
        if (!state.equals(address.state)) return false;
        if (!street.equals(address.street)) return false;
        return zip.equals(address.zip);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + zip.hashCode();
        return result;
    }
}