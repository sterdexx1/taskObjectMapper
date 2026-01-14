package com.project.taskObjectMapper.controller;

import com.project.taskObjectMapper.entity.Order;
import com.project.taskObjectMapper.exception.ExistOrderException;
import com.project.taskObjectMapper.json.OrderJson;
import com.project.taskObjectMapper.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<String>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<String> ordersJson = orders.stream()
                .map(OrderJson::toJson)
                .toList();
        return new ResponseEntity<>(ordersJson, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id).get();
        return new ResponseEntity<>(OrderJson.toJson(order), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<String> addNewOrder(@RequestBody @Valid String orderJson) {
        Order order = OrderJson.fromJson(orderJson);
        if (orderService.getOrderById(order.getId()).isPresent()) {
            throw new ExistOrderException("The order already exists with id " + order.getId());
        }
        orderService.addNewOrder(order);
        return new ResponseEntity<>("Order: " + order.getId() + " is added", HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>("Order with id " + id + " is deleted", HttpStatus.OK);
    }

    @PutMapping("/orders")
    public ResponseEntity<String> updateOrder(@RequestBody @Valid String orderJson) {
        Order order = OrderJson.fromJson(orderJson);
        orderService.updateOrder(order);
        return new ResponseEntity<>("Order: " + order.getId() + " is updated", HttpStatus.OK);
    }
}
