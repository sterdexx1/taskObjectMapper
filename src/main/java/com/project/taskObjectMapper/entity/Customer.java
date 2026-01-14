package com.project.taskObjectMapper.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @NotBlank(message = "Field name couldn't be empty")
    private String name;
    @Column(name = "surname")
    @NotBlank(message = "Field surname couldn't be empty")
    private String surname;
    @Column(name = "email")
    @NotBlank(message = "Field email couldn't be empty")
    @Email
    private String email;
    @Column(name = "number")
    @NotBlank(message = "Field number couldn't be empty")
    @Pattern(regexp = "\\+\\d{1,11}", message = "phone number must be in format: +XXXXXXXXXXX")
    private String number;

    @OneToMany(mappedBy = "customer",
            fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Order> orders;
}
