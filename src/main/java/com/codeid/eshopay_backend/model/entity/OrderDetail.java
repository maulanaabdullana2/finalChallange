package com.codeid.eshopay_backend.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table (name = "order_detail",schema = "oe")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;

    private Double price;
    private Long quantity;
    private Double discount;
    private Double voucher;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;


    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

}
