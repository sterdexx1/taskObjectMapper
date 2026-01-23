package com.project.taskObjectMapper.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.taskObjectMapper.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_table")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "address")
    @NotBlank(message = "Address couldn't be empty")
    private String address;
    @Column(name = "cost")
    @NotBlank(message = "Cost couldn't be empty")
    @Positive(message = "Cost must be more than 0")
    private BigDecimal cost;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotBlank(message = "Status couldn't be empty")
    private OrderStatus status;
    @Column(name = "order_data")
    @NotBlank(message = "Order data couldn't be empty")
    private LocalDateTime order_data;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "order",
            fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Product> products;
}
