package com.codeid.eshopay_backend.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.codeid.eshopay_backend.model.enumeration.EnumTypeBank;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders", schema = "oe")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "required_date")
    private LocalDateTime requiredDate;
    @Column(name = "shipped_date")
    private LocalDateTime shippedDate;
    @Column(name = "freight")
    private Double freight;
    @Column(name = "ship_name")
    private String shipName;
    @Column(name = "total_discount")
    private Double totalDiscount;
    @Column(name = "total_amount")
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private EnumTypeBank paymentType;
    @Column(name = "card_no")
    private String CardNo;
    @Column(name = "transac_no")
    private String transacNo;
    @Column(name = "transac_date")
    private LocalDateTime transacDate;
    @Column(name = "ref_no")
    private String refNo;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "bank_code")
    private Bank Bank;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

}
