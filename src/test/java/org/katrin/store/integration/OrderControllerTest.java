package org.katrin.store.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katrin.store.dto.OrderDto;
import org.katrin.store.dto.OrderItemDto;
import org.katrin.store.dto.ProductDto;
import org.katrin.store.repository.OrderRepository;
import org.katrin.store.service.OrderItemService;
import org.katrin.store.service.OrderService;
import org.katrin.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private OrderDto orderDto1;
    private OrderDto orderDto2;

    @BeforeEach
    public void init() {
        orderRepository.deleteAll();
        orderDto1 = OrderDto.builder()
                .customerName("Customer 1")
                .checkoutDate(LocalDateTime.of(12, 12, 12, 12, 12))
                .build();
        orderDto2 = OrderDto.builder()
                .customerName("Customer 2")
                .build();
    }

    @Test
    public void getAllTest() throws Exception {
        orderDto2.setCheckoutDate(LocalDateTime.now());

        orderDto1 = orderService.save(orderDto1);
        orderDto2 = orderService.save(orderDto2);

        mockMvc.perform(get("/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(orderDto1, orderDto2)), true));
    }

    @Test
    public void getByIdTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        int id = orderDto1.getId();

        mockMvc.perform(get("/orders/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderDto1), true));
    }

    @Test
    public void saveTest() throws Exception {
        mockMvc.perform(post("/orders")
                        .content(mapper.writeValueAsString(orderDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.customerName").value(orderDto2.getCustomerName()))
                .andExpect(jsonPath("$.status").value("IN_PROCESSING"))
                .andExpect(jsonPath("$.checkoutDate").exists());
    }

    @Test
    public void updateWithoutItemsTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        orderDto1.setCustomerName("Customer 1 UPDATED");

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderDto1)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderDto1), true));
    }

    @Test
    public void getItemsTest() throws Exception {
        orderDto2 = orderService.save(orderDto2);
        int orderId = orderDto2.getId();

        ProductDto productDto = ProductDto.builder().name("Product 2").stockQuantity(12).build();
        productDto = productService.save(productDto);

        OrderItemDto orderItemDto = OrderItemDto.builder().quantity(456).price(34.12).productId(productDto.getId()).build();
        orderDto2 = orderService.addItem(orderDto2.getId(), orderItemDto);

        int orderItemId = orderDto2.getItems().getFirst();
        orderItemDto = orderItemService.getById(orderItemId);

        mockMvc.perform(get("/orders/{id}/items", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(orderItemDto))));
    }

    @Test
    public void addItemTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        int orderId = orderDto1.getId();

        ProductDto productDto = ProductDto.builder().name("Product 1").stockQuantity(12).build();
        productDto = productService.save(productDto);

        OrderItemDto orderItemDto = OrderItemDto.builder()
                .quantity(456)
                .price(34.12)
                .productId(productDto.getId())
                .build();

        mockMvc.perform(post("/orders/{id}/items", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderItemDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.items").isNotEmpty());

        orderDto1 = orderService.getById(orderId);
        int orderItemId = orderDto1.getItems().getFirst();

        mockMvc.perform(get("/items/{id}", orderItemId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.price").value(orderItemDto.getPrice()))
                .andExpect(jsonPath("$.quantity").value(orderItemDto.getQuantity()))
                .andExpect(jsonPath("$.orderId").value(orderId));
    }

    @Test
    public void deleteTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        int id = orderDto1.getId();

        mockMvc.perform(delete("/orders/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
