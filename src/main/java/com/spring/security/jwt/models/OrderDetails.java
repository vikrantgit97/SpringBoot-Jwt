package com.spring.security.jwt.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orderDetails_tbl")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@BatchSize(size = 2)
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "idGen")    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "idGen", strategy = "increment")
    private Integer id;

    private Integer orderNumber;

    @NotNull(message = "Product code cannot be null")
    private Integer productCode;

    @Min(value = 1, message = "Quantity ordered must be at least 1")
    private Integer quantityOrdered;

    @Min(value = 100, message = "Price each must be at least 100.00")
    private Double priceEach;

    @ManyToOne
    @JoinColumn(name = "fk_orderNumber")
    @JsonBackReference
    private Order order;

    @JsonIgnore
    @ManyToOne
    private Product product;

}