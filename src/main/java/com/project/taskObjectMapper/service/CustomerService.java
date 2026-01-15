package com.project.taskObjectMapper.service;

import com.project.taskObjectMapper.entity.Customer;
import com.project.taskObjectMapper.exception.ExistCustomerException;
import com.project.taskObjectMapper.exception.NotFoundCustomerException;
import com.project.taskObjectMapper.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(@Valid Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundCustomerException("Customer not found with id: " + id));
    }

    @Transactional
    public void addNewCustomer(@Valid Customer customer) {
        if (customerRepository.existsById(customer.getId())){
            throw new ExistCustomerException("The customer already exists with id " + customer.getId());
        }
        customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        if (!customerRepository.existsById(id)){
            throw new NotFoundCustomerException("Not found customer with id " + id);
        }
        customerRepository.deleteById(id);
    }

    @Transactional
    public void updateCustomer(@Valid Customer customer) {
        if (!customerRepository.existsById(customer.getId())){
            throw new NotFoundCustomerException("Not found customer with id " + customer.getId());
        }
        customerRepository.save(customer);
    }
}
