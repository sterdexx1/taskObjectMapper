package com.project.taskObjectMapper.service;

import com.project.taskObjectMapper.entity.Product;
import com.project.taskObjectMapper.exception.ExistProductException;
import com.project.taskObjectMapper.exception.NotFoundProductException;
import com.project.taskObjectMapper.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(@Valid Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundProductException("Product not found with id: " + id));
    }

    @Transactional
    public void addNewProduct(@Valid Product product) {
        if (productRepository.existsById(product.getId())){
            throw new ExistProductException("The product already exists with id " + product.getId());
        }
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)){
            throw new NotFoundProductException("Not found product with id " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(@Valid Product product) {
        if (!productRepository.existsById(product.getId())){
            throw new NotFoundProductException("Not found product with id " + product.getId());
        }
        productRepository.save(product);
    }
}
