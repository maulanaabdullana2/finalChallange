package com.codeid.eshopay_backend.model.dto;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DependentBulkDto {
    private Long employeeId;
    private List<DependentDto> dependents;
}
