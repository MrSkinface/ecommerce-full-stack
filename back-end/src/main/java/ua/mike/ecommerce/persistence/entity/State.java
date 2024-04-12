package ua.mike.ecommerce.persistence.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;


@Table
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
public class State extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}