package com.codeid.eshopay_backend.model.entity;
import com.codeid.eshopay_backend.model.enumeration.EnumRalationship;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "dependents", schema = "hr")
public class Dependent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dependent_id")
    private Long dependentId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "relationship")
    private EnumRalationship relationship;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
