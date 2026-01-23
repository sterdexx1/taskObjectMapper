package com.project.taskObjectMapper;

import com.project.taskObjectMapper.entity.Product;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ProductService productService;

    @Test
    public void testAddNewProduct() throws Exception {
        String jsonContent = """
                {
                    "id": 1,
                    "name": "Notebook Lenovo IdeaPad",
                    "description": "15.6, 8GB RAM, 256GB SSD",
                    "price": 24999.99,
                    "amount": 1
                }
                """;
        doNothing().when(productService).addNewProduct(any(Product.class));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("Product: Notebook Lenovo IdeaPad is added"));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = List.of(
                new Product(1, "Notebook Lenovo IdeaPad",
                        "15.6, 8GB RAM, 256GB SSD",
                        BigDecimal.valueOf(24999.99), 1, null),
                new Product(1, "Notebook Lenovo IdeaPad",
                        "15.6, 8GB RAM, 256GB SSD",
                        BigDecimal.valueOf(24999.99), 1, null),
                new Product(1, "Notebook Lenovo IdeaPad",
                        "15.6, 8GB RAM, 256GB SSD",
                        BigDecimal.valueOf(24999.99), 1, null)
        );
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));


    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product(1, "Notebook Lenovo IdeaPad",
                "15.6, 8GB RAM, 256GB SSD",
                BigDecimal.valueOf(24999.99), 1, null);
        when(productService.getProductById(1)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        String jsonContent = """
                {
                    "id": 1,
                    "name": "Notebook Lenovo IdeaPad",
                    "description": "15.6, 8GB RAM, 256GB SSD",
                    "price": 24999.99,
                    "amount": 1
                }
                """;
        doNothing().when(productService).addNewProduct(any(Product.class));

        mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("Product: Notebook Lenovo IdeaPad is updated"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(any(Integer.class));

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product with id 1 is deleted"));
    }
}
