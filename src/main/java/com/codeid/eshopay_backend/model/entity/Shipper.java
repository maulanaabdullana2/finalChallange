package com.codeid.eshopay_backend.model.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor 
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "shippers",schema = "oe")
public class Shipper extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id" )
    public Long shipperId;

    @Nonnull
    @Column(name = "company_name")
    private String companyName;

    @Nonnull
    @Column(name = "phone")
    private String phone;
}
