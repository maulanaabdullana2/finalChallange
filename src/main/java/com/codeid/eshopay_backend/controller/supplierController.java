package com.codeid.eshopay_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeid.eshopay_backend.model.dto.supplierDto;
import com.codeid.eshopay_backend.model.response.ApiResponse;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.supplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/supplier/")
@Slf4j
@RequiredArgsConstructor
public class supplierController extends BaseCrudController<supplierDto, Long> {
    public final supplierService supplierService;

    @Override
    protected BaseCrudService<supplierDto, Long> getService() {
        return supplierService;
    }

    @Override
    public ResponseEntity create(@Valid supplierDto entity) {
        try {
            supplierDto createdSupplier = super.create(entity).getBody();
            ApiResponse<supplierDto> response = new ApiResponse<>(
                "success",
                "Supplier created successfully",
                createdSupplier
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<supplierDto> errorResponse = new ApiResponse<>(
                "error",
                e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @Override
    public ResponseEntity delete(Long id) {
        try {
            super.delete(id);
            ApiResponse<Void> response = new ApiResponse<>(
                    "success",
                    "Supplier deleted successfully",
                    null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>(
                    "error",
                    e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity getAll() {
        List<supplierDto> supplierList = super.getAll().getBody();
        try {
            ApiResponse<List<supplierDto>> response = new ApiResponse<>(
                    "success",
                    "Supplier data retrieved successfully",
                    supplierList);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<supplierDto> errorResponse = new ApiResponse<>(
                    "error",
                    e.getMessage(),
                    null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @Override
    public ResponseEntity getById(Long id) {
        try {
            supplierDto supplier = (supplierDto) super.getById(id).getBody();

            ApiResponse<supplierDto> response = new ApiResponse<>(
                    "success",
                    "Supplier data retrieved successfully",
                    supplier);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<supplierDto> errorResponse = new ApiResponse<>(
                    "error",
                    e.getMessage(),
                    null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity update(Long id, @Valid supplierDto entity) {
        try {
            supplierDto updatedSupplier = super.update(id, entity).getBody();
            ApiResponse<supplierDto> response = new ApiResponse<>(
                    "success",
                    "Supplier updated successfully",
                    updatedSupplier);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<supplierDto> errorResponse = new ApiResponse<>(
                    "error",
                    e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
