package com.project.taskObjectMapper.controller;

import com.project.taskObjectMapper.entity.Customer;
import com.project.taskObjectMapper.json.CustomerJson;
import com.project.taskObjectMapper.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {


    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<String>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<String> customersJson = customers.stream()
                .map(CustomerJson::toJson)
                .toList();
        return new ResponseEntity<>(customersJson, HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<String> getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(CustomerJson.toJson(customer), HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<String> addNewCustomer(@RequestBody @Valid String customerJson) {
        Customer customer = CustomerJson.fromJson(customerJson);
        customerService.addNewCustomer(customer);
        return new ResponseEntity<>("Customer: " + customer.getName() + " is added", HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>("Customer with id " + id + " is deleted", HttpStatus.OK);
    }

    @PutMapping("/customers")
    public ResponseEntity<String> updateCustomer(@RequestBody @Valid String customerJson) {
        Customer customer = CustomerJson.fromJson(customerJson);
        customerService.updateCustomer(customer);
        return new ResponseEntity<>("Customer: " + customer.getName() + " is updated", HttpStatus.OK);
    }
}
