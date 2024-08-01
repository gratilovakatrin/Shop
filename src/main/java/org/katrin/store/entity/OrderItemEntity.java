package org.katrin.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "order_item", schema = "public")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    @Min(0)
    private double price;

    @Column(nullable = false)
    @Min(0)
    private int quantity;

    @Override
    public String toString() {
        return "OrderItemEntity{" +
                "id=" + id +
                ", order=" + order.getId() +
                ", product=" + product.getId() +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
