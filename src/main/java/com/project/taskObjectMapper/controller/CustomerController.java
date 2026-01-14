package com.project.taskObjectMapper.controller;

import com.project.taskObjectMapper.entity.Customer;
import com.project.taskObjectMapper.exception.ExistCustomerException;
import com.project.taskObjectMapper.json.CustomerJson;
import com.project.taskObjectMapper.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
        Customer customer = customerService.getCustomerById(id).get();
        return new ResponseEntity<>(CustomerJson.toJson(customer), HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<String> addNewCustomer(@RequestBody @Valid String customerJson) {
        Customer customer = CustomerJson.fromJson(customerJson);
        if (customerService.getCustomerById(customer.getId()).isPresent()) {
            throw new ExistCustomerException("The customer already exists with id " + customer.getId());
        }
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
