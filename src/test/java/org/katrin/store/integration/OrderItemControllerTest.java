package org.katrin.store.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katrin.store.dto.OrderDto;
import org.katrin.store.dto.OrderItemDto;
import org.katrin.store.dto.ProductDto;
import org.katrin.store.repository.OrderItemRepository;
import org.katrin.store.service.OrderItemService;
import org.katrin.store.service.OrderService;
import org.katrin.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class OrderItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    OrderItemRepository orderItemRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    private static OrderDto orderDto;
    private static OrderItemDto orderItemDto;

    @BeforeAll
    public static void setUp(@Autowired OrderService orderService, @Autowired ProductService productService) {
        orderDto = OrderDto.builder()
                .customerName("Customer 1").checkoutDate(LocalDateTime.now()).build();
        orderDto = orderService.save(orderDto);

        ProductDto productDto = ProductDto.builder().name("Product 1").stockQuantity(12).build();
        productDto = productService.save(productDto);

        orderItemDto = OrderItemDto.builder().quantity(456).price(34.12).productId(productDto.getId()).build();
    }

    @BeforeEach
    public void init() {
        orderItemRepository.deleteAll();

        orderDto = orderService.addItem(orderDto.getId(), orderItemDto);

        int orderItemId = orderDto.getItems().getFirst();
        orderItemDto = orderItemService.getById(orderItemId);
    }

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(orderItemDto)), true));
    }

    @Test
    public void getByIdTest() throws Exception {
        mockMvc.perform(get("/items/{id}", orderItemDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderItemDto), true));
    }

    @Test
    public void updateTest() throws Exception {
        orderItemDto.setQuantity(13);

        mockMvc.perform(put("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderItemDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderItemDto), true));
    }

    @Test
    public void deleteTest() throws Exception {
        int id = orderItemDto.getId();

        mockMvc.perform(delete("/items/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
