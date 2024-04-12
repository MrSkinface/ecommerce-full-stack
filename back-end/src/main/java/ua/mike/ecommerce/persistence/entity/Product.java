package ua.mike.ecommerce.persistence.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Table
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    private String imageUrl;

    private boolean active;

    @Column(nullable = false)
    private int unitsInStock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
