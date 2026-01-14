package com.project.taskObjectMapper;

import com.project.taskObjectMapper.entity.Order;
import com.project.taskObjectMapper.enums.OrderStatus;
import com.project.taskObjectMapper.service.CustomerService;
import com.project.taskObjectMapper.service.OrderService;
import com.project.taskObjectMapper.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ProductService productService;

    @Test
    public void testAddNewOrder() throws Exception {
        String jsonContent = """
                {
                    "id": 1,
                    "address": "Moscow, st. Pushkina, 10",
                    "cost": 27598.99,
                    "status": "OK",
                    "order_data": "2025-03-15T10:30:00"
                }
                """;
        doNothing().when(orderService).addNewOrder(any(Order.class));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("Order: 1 is added"));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        List<Order> orders = List.of(
                new Order(1, "Moscow, st. Pushkina, 10", BigDecimal.valueOf(27598.99),
                        OrderStatus.OK, LocalDateTime.of(2025,03,
                        15,10,30,00), null, null),
                new Order(1, "Moscow, st. Pushkina, 10", BigDecimal.valueOf(27598.99),
                        OrderStatus.OK, LocalDateTime.of(2025,03,
                        15,10,30,00), null, null),
                new Order(1, "Moscow, st. Pushkina, 10", BigDecimal.valueOf(27598.99),
                        OrderStatus.OK, LocalDateTime.of(2025,03,
                        15,10,30,00), null, null)
        );
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));


    }

    @Test
    public void testGetOrderById() throws Exception {
        Order order = new Order(1, "Moscow, st. Pushkina, 10", BigDecimal.valueOf(27598.99),
                OrderStatus.OK, LocalDateTime.of(2025,03,
                15,10,30,00), null, null);
        when(orderService.getOrderById(1)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        String jsonContent = """
                {
                    "id": 1,
                    "address": "Moscow, st. Pushkina, 10",
                    "cost": 27598.99,
                    "status": "OK",
                    "order_data": "2025-03-15T10:30:00"
                }
                """;
        doNothing().when(orderService).addNewOrder(any(Order.class));

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("Order: 1 is updated"));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(any(Integer.class));

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order with id 1 is deleted"));
    }
}
