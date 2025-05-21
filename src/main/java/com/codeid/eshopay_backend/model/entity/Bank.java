package com.codeid.eshopay_backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Builder
@Table (name = "bank",schema = "oe")
public class Bank {


    @Id
    @Column (name = "bank_code")
    public String bankCode;
    @Column (name = "bank_name")
    public String bankName;
}
