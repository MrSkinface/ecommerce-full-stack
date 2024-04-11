package ua.mike.ecommerce.models;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "country")
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String code;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Set<State> states;

}