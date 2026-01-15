package com.project.taskObjectMapper;

import com.project.taskObjectMapper.entity.Customer;
import com.project.taskObjectMapper.exception.NotFoundCustomerException;
import com.project.taskObjectMapper.repository.CustomerRepository;
import com.project.taskObjectMapper.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testFindExistCustomerById() {
        Customer customer = new Customer(1, "Ivan", "Ivanov",
                "ivanov@example.com","+79999999999",null);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Customer result = customerService.getCustomerById(1);
        assertEquals(1, result.getId());
        assertEquals("Ivan", result.getName());
        assertEquals("Ivanov", result.getSurname());
        assertEquals("ivanov@example.com", result.getEmail());
        assertEquals("+79999999999", result.getNumber());
        assertNull(result.getOrders());
    }

    @Test
    public void testFindNonExistingCustomerById(){
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundCustomerException.class, () -> customerService.getCustomerById(1));
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveCustomer() {
        Customer customer = new Customer(1, "Ivan", "Ivanov",
                "ivanov@example.com","+79999999999",null);
        customerService.addNewCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testDeleteExistCustomer() {
        when(customerRepository.existsById(1)).thenReturn(true);

        customerService.deleteCustomer(1);
        verify(customerRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNonExistingCustomer(){
        when(customerRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundCustomerException.class, () -> customerService.deleteCustomer(1));
        verify(customerRepository, never()).deleteById(1);
    }

    @Test
    public void testUpdateExistCustomer(){
        when(customerRepository.existsById(1)).thenReturn(true);

        Customer newCustomer = new Customer(1, "Ivan", "Ivanov",
                "ivanov@example.com","+79999999999",null);

        customerService.updateCustomer(newCustomer);
        verify(customerRepository, times(1)).save(newCustomer);
    }

    @Test
    public void testUpdateNonExistingCustomer(){
        when(customerRepository.existsById(1)).thenReturn(false);
        assertThrows(NotFoundCustomerException.class, () ->
                customerService.updateCustomer(new Customer(1, "Ivan", "Ivanov",
                        "ivanov@example.com","+79999999999",null)));
        verify(customerRepository, never()).save(any(Customer.class));
    }
}
