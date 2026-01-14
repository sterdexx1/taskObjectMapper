package com.project.taskObjectMapper;

import com.project.taskObjectMapper.entity.Product;
import com.project.taskObjectMapper.exception.NotFoundProductException;
import com.project.taskObjectMapper.repository.ProductRepository;
import com.project.taskObjectMapper.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testFindExistProductById() {
        Product product = new Product(1, "Notebook Lenovo IdeaPad",
                "15.6, 8GB RAM, 256GB SSD",
                BigDecimal.valueOf(24999.99), 1, null);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Optional<Product> result = productService.getProductById(1);
        assertEquals(1, result.get().getId());
        assertEquals("Notebook Lenovo IdeaPad", result.get().getName());
        assertEquals("15.6, 8GB RAM, 256GB SSD", result.get().getDescription());
        assertEquals(BigDecimal.valueOf(24999.99), result.get().getPrice());
        assertEquals(1, result.get().getAmount());
        assertNull(result.get().getOrder());
    }

    @Test
    public void testFindNonExistingProductById(){
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundProductException.class, () -> productService.getProductById(1));
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product(1, "Notebook Lenovo IdeaPad",
                "15.6, 8GB RAM, 256GB SSD",
                BigDecimal.valueOf(24999.99), 1, null);
        productService.addNewProduct(product);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteExistProduct() {
        when(productRepository.existsById(1)).thenReturn(true);

        productService.deleteProduct(1);
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNonExistingProduct(){
        when(productRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundProductException.class, () -> productService.deleteProduct(1));
        verify(productRepository, never()).deleteById(1);
    }

    @Test
    public void testUpdateExistProduct(){
        when(productRepository.existsById(1)).thenReturn(true);

        Product newProduct = new Product(1, "Notebook Lenovo IdeaPad",
                "15.6, 8GB RAM, 256GB SSD",
                BigDecimal.valueOf(24999.99), 1, null);

        productService.updateProduct(newProduct);
        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    public void testUpdateNonExistingProduct(){
        when(productRepository.existsById(1)).thenReturn(false);
        assertThrows(NotFoundProductException.class, () ->
                productService.updateProduct(new Product(1, "Notebook Lenovo IdeaPad",
                        "15.6, 8GB RAM, 256GB SSD",
                        BigDecimal.valueOf(24999.99), 1, null)));
        verify(productRepository, never()).save(any(Product.class));
    }
}
