package com.project.taskObjectMapper.service;

import com.project.taskObjectMapper.entity.Order;
import com.project.taskObjectMapper.exception.ExistOrderException;
import com.project.taskObjectMapper.exception.NotFoundOrderException;
import com.project.taskObjectMapper.repository.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(@Valid Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundOrderException("Order not found with id: " + id));
    }

    @Transactional
    public void addNewOrder(@Valid Order order) {
        if (orderRepository.existsById(order.getId())){
            throw new ExistOrderException("The order already exists with id " + order.getId());
        }
        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)){
            throw new NotFoundOrderException("Not found order with id " + id);
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public void updateOrder(@Valid Order order) {
        if (!orderRepository.existsById(order.getId())){
            throw new NotFoundOrderException("Not found order with id " + order.getId());
        }
        orderRepository.save(order);
    }
}
