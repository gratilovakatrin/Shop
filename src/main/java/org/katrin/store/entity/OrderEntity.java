package org.katrin.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.katrin.store.dto.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders", schema = "public")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private int id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @JsonProperty("status")
    @Column(nullable = false)
    private OrderStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "checkout_date", nullable = false)
    @ColumnDefault("CURRENT_DATE")
    private LocalDateTime checkoutDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "order_id")
    private List<OrderItemEntity> items = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        checkoutDate = Optional.ofNullable(checkoutDate).orElse(LocalDateTime.now());
        status = Optional.ofNullable(status).orElse(OrderStatus.IN_PROCESSING);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", status=" + status +
                ", checkoutDate=" + checkoutDate +
                ", items=" + items.stream().map(OrderItemEntity::getId).toList() +
                '}';
    }
}