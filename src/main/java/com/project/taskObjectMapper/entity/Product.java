package com.project.taskObjectMapper.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @NotBlank(message = "Name couldn't be empty")
    private String name;
    @Column(name = "description")
    @NotBlank(message = "Description couldn't be empty")
    private String description;
    @Column(name = "price")
    @NotBlank(message = "Price couldn't be empty")
    @Positive(message = "Price must be more than 0")
    private BigDecimal price;
    @Column(name = "amount")
    @NotBlank(message = "Amount couldn't be empty")
    @Positive(message = "Amount must be more than 0")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
}
