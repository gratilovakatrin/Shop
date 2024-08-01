package org.katrin.store.service;

import lombok.AllArgsConstructor;
import org.katrin.store.converter.OrderItemConverter;
import org.katrin.store.dto.OrderItemDto;
import org.katrin.store.entity.OrderItemEntity;
import org.katrin.store.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemDto getById(int id) {
        return orderItemRepository.findById(id).map(OrderItemConverter::toDto).orElseThrow();
    }

    public List<OrderItemDto> getAll() {
        return orderItemRepository.findAll().stream().map(OrderItemConverter::toDto).toList();
    }

    public List<OrderItemDto> findByOrderId(int orderId) {
        return orderItemRepository.findByOrderId(orderId).stream().map(OrderItemConverter::toDto).toList();
    }

    public OrderItemDto update(OrderItemDto orderItemDto) {
        OrderItemEntity orderItemEntity = orderItemRepository.save(OrderItemConverter.toEntity(orderItemDto));
        return OrderItemConverter.toDto(orderItemEntity);
    }

    public void delete(int id) {
        orderItemRepository.deleteById(id);
    }
}
