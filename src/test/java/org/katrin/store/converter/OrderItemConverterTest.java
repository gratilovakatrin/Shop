package org.katrin.store.converter;

import org.junit.jupiter.api.Test;
import org.katrin.store.dto.OrderItemDto;
import org.katrin.store.entity.OrderEntity;
import org.katrin.store.entity.OrderItemEntity;
import org.katrin.store.entity.ProductEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemConverterTest {
    @Test
    public void toDtoTest() {
        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                .id(1)
                .price(12.2)
                .quantity(34)
                .order(OrderEntity.builder().id(2).build())
                .product(ProductEntity.builder().id(3).build())
                .build();

        OrderItemDto orderItemDto = OrderItemConverter.toDto(orderItemEntity);

        assertEquals(orderItemEntity.getId(), orderItemDto.getId());
        assertEquals(orderItemEntity.getPrice(), orderItemDto.getPrice());
        assertEquals(orderItemEntity.getQuantity(), orderItemDto.getQuantity());
        assertEquals(orderItemEntity.getOrder().getId(), orderItemDto.getOrderId());
        assertEquals(orderItemEntity.getProduct().getId(), orderItemDto.getProductId());
    }

    @Test
    public void toEntityTest() {
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .id(1)
                .price(12.2)
                .quantity(34)
                .orderId(2)
                .productId(3)
                .build();

        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        assertEquals(orderItemDto.getId(), orderItemEntity.getId());
        assertEquals(orderItemDto.getPrice(), orderItemEntity.getPrice());
        assertEquals(orderItemDto.getQuantity(), orderItemEntity.getQuantity());
        assertEquals(orderItemDto.getOrderId(), orderItemEntity.getOrder().getId());
        assertEquals(orderItemDto.getProductId(), orderItemEntity.getProduct().getId());
    }
}
