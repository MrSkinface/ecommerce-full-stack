package ua.mike.ecommerce.persistence.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Table
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
public class Address extends BaseEntity {

    @Column(nullable = false)
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
    @Builder.Default
    private Set<Order> billing = new HashSet<>();

    @OneToMany(mappedBy = "shippingAddress")
    @Builder.Default
    private Set<Order> shipping = new HashSet<>();
}