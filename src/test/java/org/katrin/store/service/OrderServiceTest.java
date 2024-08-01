package org.katrin.store.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.katrin.store.converter.OrderConverter;
import org.katrin.store.converter.OrderItemConverter;
import org.katrin.store.dto.OrderDto;
import org.katrin.store.dto.OrderItemDto;
import org.katrin.store.entity.OrderEntity;
import org.katrin.store.entity.OrderItemEntity;
import org.katrin.store.repository.OrderRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    public void getAllTest_ok() {
        List<OrderDto> orderDtos = List.of(OrderDto.builder().items(new ArrayList<>()).build(),
                OrderDto.builder().items(new ArrayList<>()).build());
        List<OrderEntity> orderEntities = List.of(OrderEntity.builder().items(new ArrayList<>()).build(),
                OrderEntity.builder().items(new ArrayList<>()).build());

        when(orderRepository.findAll()).thenReturn(orderEntities);

        List<OrderDto> actual = orderService.getAll();

        assertEquals(actual, orderDtos);
        assertEquals(actual.size(), orderEntities.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void getByIdTest_ok() {
        int id = 1;
        OrderDto orderDto = OrderDto.builder().id(id).items(new ArrayList<>()).build();
        OrderEntity orderEntity = OrderConverter.toEntity(orderDto);

        when(orderRepository.findById(1)).thenReturn(Optional.ofNullable(orderEntity));

        OrderDto actual = orderService.getById(id);

        assertEquals(actual, orderDto);
        assertEquals(actual.getId(), id);
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    public void getByIdTest_notFound() {
        int id = 1;

        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getById(id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void saveTest_ok() {
        int id = 1;
        OrderDto orderDto = OrderDto.builder().id(id).items(new ArrayList<>()).build();
        OrderEntity orderEntity = OrderConverter.toEntity(orderDto);

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderDto actual = orderService.save(orderDto);

        assertEquals(actual, orderDto);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    public void updateWithoutItemsTest_ok() {
        int id = 1;
        OrderDto orderDto = OrderDto.builder().id(id).items(new ArrayList<>()).build();
        OrderEntity orderEntity = OrderConverter.toEntity(orderDto);

        when(orderRepository.updateWithoutItems(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderDto actual = orderService.updateWithoutItems(orderDto);

        assertEquals(actual, orderDto);
        verify(orderRepository, times(1)).updateWithoutItems(any(OrderEntity.class));
    }

    @Test
    public void addItemTest_ok() {
        int orderId = 1;
        int orderItemId = 12;

        OrderItemDto orderItemDto = OrderItemDto.builder().id(orderItemId).build();
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        OrderDto orderDto = OrderDto.builder().id(orderId).items(List.of(orderItemId)).build();
        OrderEntity orderEntity = OrderEntity.builder().id(orderId).items(List.of(orderItemEntity)).build();

        when(orderRepository.addItem(orderId, orderItemEntity)).thenReturn(orderEntity);

        OrderDto actual = orderService.addItem(orderId, orderItemDto);

        assertEquals(actual, orderDto);
        assertTrue(actual.getItems().contains(orderItemId));
        verify(orderRepository, times(1)).addItem(orderId, orderItemEntity);
    }

    @Test
    public void deleteByIdTest_ok() {
        int id = 1;

        doNothing().when(orderRepository).deleteById(id);
        orderService.delete(id);

        verify(orderRepository, times(1)).deleteById(id);
    }
}
