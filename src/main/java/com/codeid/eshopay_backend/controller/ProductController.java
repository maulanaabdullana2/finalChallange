package com.codeid.eshopay_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeid.eshopay_backend.model.dto.productDto;
import com.codeid.eshopay_backend.model.dto.supplierDto;
import com.codeid.eshopay_backend.model.response.ApiResponse;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.productService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product/")
@Slf4j
@RequiredArgsConstructor
public class ProductController extends BaseCrudController<productDto, Long> {
    private final productService productService;

    @Override
    protected BaseCrudService<productDto, Long> getService() {
        return productService;
    }

    @Override
    public ResponseEntity getAll() {
        List<productDto> supplierList = super.getAll().getBody();
        try {
            ApiResponse<List<productDto>> response = new ApiResponse<>(
                    "success",
                    "Product data retrieved successfully",
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
            productDto product = (productDto) super.getById(id).getBody();

            ApiResponse<productDto> response = new ApiResponse<>(
                    "success",
                    "Product data retrieved successfully",
                    product);

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
    public ResponseEntity create(@Valid productDto entity) {
        try {
            productDto product = productService.save(entity);
            ApiResponse<productDto> response = new ApiResponse<>(
                "success",
                "Product created successfully",
                product);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>(
                "error",
                e.getMessage(),
                null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
    }

    @Override
    public ResponseEntity<productDto> update(Long id, @Valid productDto entity) {
        return super.update(id, entity);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }


    

}
