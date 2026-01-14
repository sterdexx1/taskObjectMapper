package com.project.taskObjectMapper.controller;

import com.project.taskObjectMapper.entity.Product;
import com.project.taskObjectMapper.exception.ExistProductException;
import com.project.taskObjectMapper.json.ProductJson;
import com.project.taskObjectMapper.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<String>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<String> productsJson = products.stream()
                .map(ProductJson::toJson)
                .toList();
        return new ResponseEntity<>(productsJson, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id).get();
        return new ResponseEntity<>(ProductJson.toJson(product), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<String> addNewProduct(@RequestBody @Valid String productJson) {
        Product product = ProductJson.fromJson(productJson);
        if (productService.getProductById(product.getId()).isPresent()) {
            throw new ExistProductException("The product already exists with id " + product.getId());
        }
        productService.addNewProduct(product);
        return new ResponseEntity<>("Product: " + product.getName() + " is added", HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product with id " + id + " is deleted", HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<String> updateProduct(@RequestBody @Valid String productJson) {
        Product product = ProductJson.fromJson(productJson);
        productService.updateProduct(product);
        return new ResponseEntity<>("Product: " + product.getName() + " is updated", HttpStatus.OK);
    }
}
