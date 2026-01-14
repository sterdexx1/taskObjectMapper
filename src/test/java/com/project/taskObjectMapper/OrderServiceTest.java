package com.project.taskObjectMapper;

import com.project.taskObjectMapper.entity.Order;
import com.project.taskObjectMapper.enums.OrderStatus;
import com.project.taskObjectMapper.exception.NotFoundOrderException;
import com.project.taskObjectMapper.repository.OrderRepository;
import com.project.taskObjectMapper.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testFindExistOrderById() {
        Order order = new Order(1, "Moscow, st. Pushkina, 10", BigDecimal.valueOf(27598.99),
                OrderStatus.OK, LocalDateTime.of(2025,03,
                15,10,30,00), null, null);

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        Optional<Order> result = orderService.getOrderById(1);
        assertEquals(1, result.get().getId());
        assertEquals("Moscow, st. Pushkina, 10", result.get().getAddress());
        assertEquals(BigDecimal.valueOf(27598.99), result.get().getCost());
        assertEquals(OrderStatus.OK, result.get().getStatus());
        assertEquals(LocalDateTime.of(2025,03,
                15,10,30,00), result.get().getOrder_data());
        assertNull(result.get().getProducts());
        assertNull(result.get().getCustomer());
    }

    @Test
    public void testFindNonExistingOrderById(){
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundOrderException.class, () -> orderService.getOrderById(1));
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order(1, "Moscow, st. Pushkina, 10", BigDecimal.valueOf(27598.99),
                OrderStatus.OK, LocalDateTime.of(2025,03,
                15,10,30,00), null, null);
        orderService.addNewOrder(order);

        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testDeleteExistOrder() {
        when(orderRepository.existsById(1)).thenReturn(true);

        orderService.deleteOrder(1);
        verify(orderRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNonExistingOrder(){
        when(orderRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundOrderException.class, () -> orderService.deleteOrder(1));
        verify(orderRepository, never()).deleteById(1);
    }

    @Test
    public void testUpdateExistOrder(){
        when(orderRepository.existsById(1)).thenReturn(true);

        Order newOrder = new Order(1, "Moscow, st. Pushkina, 10", BigDecimal.valueOf(27598.99),
                OrderStatus.OK, LocalDateTime.of(2025,03,
                15,10,30,00), null, null);

        orderService.updateOrder(newOrder);
        verify(orderRepository, times(1)).save(newOrder);
    }

    @Test
    public void testUpdateNonExistingOrder(){
        when(orderRepository.existsById(1)).thenReturn(false);
        assertThrows(NotFoundOrderException.class, () ->
                orderService.updateOrder(new Order(1, "Moscow, st. Pushkina, 10", BigDecimal.valueOf(27598.99),
                        OrderStatus.OK, LocalDateTime.of(2025,03,
                        15,10,30,00), null, null)));
        verify(orderRepository, never()).save(any(Order.class));
    }
}
