package com.codeid.eshopay_backend.model.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor //empty constructor
@AllArgsConstructor //semua attribute departmentid & departmentName masuk ke constructor
@RequiredArgsConstructor // hanya attribute yg diberi annotatsi @NonNull atau private final
@Entity
@Table(name="departments",schema = "hr")
public class Department{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="department_id")
    private Long departmentId;
    
    @Nonnull
    @Column(name="department_name")
    private  String departmentName;   

    
}