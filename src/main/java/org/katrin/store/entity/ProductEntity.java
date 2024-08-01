package org.katrin.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "product", schema = "public")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "stock_quantity", nullable = false)
    @Min(0)
    private int stockQuantity;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    @Min(0)
    private double price;

    @PrePersist
    public void onCreate() {
        country = Optional.ofNullable(country).orElse("Ukraine");
    }
}
