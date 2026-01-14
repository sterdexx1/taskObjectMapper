package com.project.taskObjectMapper;

import com.project.taskObjectMapper.entity.Customer;
import com.project.taskObjectMapper.service.CustomerService;
import com.project.taskObjectMapper.service.OrderService;
import com.project.taskObjectMapper.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ProductService productService;

    @Test
    public void testAddNewCustomer() throws Exception {
        String jsonContent = """
                {
                    "id": 1,
                    "name": "Ivan",
                    "surname": "Ivanov",
                    "email": "ivanov@example.com",
                    "number": "+79999999999"
                }
                """;
        doNothing().when(customerService).addNewCustomer(any(Customer.class));

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer: Ivan is added"));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        List<Customer> customers = List.of(
                new Customer(1, "Ivan", "Ivanov",
                        "ivan.ivanov@example.com", "+79991234567", null),
                new Customer(2, "Maria", "Petrova",
                        "maria.petrova@example.com", "+79997654321", null),
                new Customer(3, "Alexey", "Sidorov",
                        "alexey.sidorov@example.com", "+79991112233", null)
        );
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));


    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer(3, "Alexey", "Sidorov",
                "alexey.sidorov@example.com", "+79991112233", null);
        when(customerService.getCustomerById(1)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        String jsonContent = """
                {
                    "id": 1,
                    "name": "Ivan",
                    "surname": "Ivanov",
                    "email": "ivanov@example.com",
                    "number": "+79999999999"
                }
                """;
        doNothing().when(customerService).addNewCustomer(any(Customer.class));

        mockMvc.perform(put("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer: Ivan is updated"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        doNothing().when(customerService).deleteCustomer(any(Integer.class));

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer with id 1 is deleted"));
    }
}
