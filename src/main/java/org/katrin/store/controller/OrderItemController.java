package org.katrin.store.controller;

import lombok.AllArgsConstructor;
import org.katrin.store.dto.OrderItemDto;
import org.katrin.store.service.OrderItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping
    public List<OrderItemDto> getAll() {
        return orderItemService.getAll();
    }

    @GetMapping("/{id}")
    public OrderItemDto getById(@PathVariable int id) {
        return orderItemService.getById(id);
    }

    @PutMapping
    public OrderItemDto update(@RequestBody OrderItemDto orderItemDto) {
        return orderItemService.update(orderItemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        orderItemService.delete(id);
    }
}
