package org.katrin.store.service;

import lombok.AllArgsConstructor;
import org.katrin.store.converter.OrderConverter;
import org.katrin.store.converter.OrderItemConverter;
import org.katrin.store.dto.OrderDto;
import org.katrin.store.dto.OrderItemDto;
import org.katrin.store.entity.OrderEntity;
import org.katrin.store.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream().map(OrderConverter::toDto).toList();
    }

    public OrderDto getById(int id) {
        return orderRepository.findById(id).map(OrderConverter::toDto).orElseThrow();
    }

    public OrderDto save(OrderDto orderDto) {
        OrderEntity orderEntity = orderRepository.save(OrderConverter.toEntity(orderDto));
        return OrderConverter.toDto(orderEntity);
    }

    public OrderDto updateWithoutItems(OrderDto orderDto) {
        OrderEntity orderEntity = orderRepository.updateWithoutItems(OrderConverter.toEntity(orderDto));
        return OrderConverter.toDto(orderEntity);
    }

    public OrderDto addItem(int orderId, OrderItemDto orderItemDto) {
        OrderEntity orderEntity = orderRepository.addItem(orderId, OrderItemConverter.toEntity(orderItemDto));
        return OrderConverter.toDto(orderEntity);
    }

    public void delete(int id) {
        orderRepository.deleteById(id);
    }
}
