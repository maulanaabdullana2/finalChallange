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
@Table(name = "suppliers", schema = "oe" )
public class Supplier extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    public Long supplierId;

    @Nonnull
    @Column(name = "company_name")
    public String companyName;
}
