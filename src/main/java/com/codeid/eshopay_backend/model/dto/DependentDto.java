package com.codeid.eshopay_backend.model.dto;

import com.codeid.eshopay_backend.model.entity.Employee;
import com.codeid.eshopay_backend.model.enumeration.EnumRalationship;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DependentDto {
    private Long dependentId;  
    private String firstName;
    private String lastName;
    private EnumRalationship relationship;
    private Long employeeId;
}
