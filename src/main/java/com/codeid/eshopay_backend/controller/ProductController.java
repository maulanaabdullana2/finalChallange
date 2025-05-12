package com.codeid.eshopay_backend.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeid.eshopay_backend.model.dto.productDto;
import com.codeid.eshopay_backend.model.dto.supplierDto;
import com.codeid.eshopay_backend.model.response.ApiResponse;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.FileStorageService;
import com.codeid.eshopay_backend.service.productService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product/")
@Slf4j
@RequiredArgsConstructor
public class ProductController extends BaseMultipartController<productDto, Long> {
    private final productService productService;
    private final FileStorageService fileStorageService;

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

    @Override
    public ResponseEntity<?> createMultipart(productDto dto, MultipartFile file, String description) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String filename = fileStorageService.storeFileWithRandomName(file);
            dto.setPhoto(filename);
            var productDto = productService.save(dto);
            ApiResponse<productDto> response = new ApiResponse<productDto>("Success", "Product created", productDto);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> viewImage(String fileName) {
        try {
            Resource resource = fileStorageService.loadFile(fileName);

            String contentType = determineContentType(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
public ResponseEntity<?> updateMultipart(Long id, productDto dto, MultipartFile file, String description) {
    try {
        productDto existingProduct = productService.findById(id);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Product not found"));
        }

        if (file != null && !file.isEmpty()) {
            String newFileName = fileStorageService.storeFileWithRandomName(file);
            dto.setPhoto(newFileName);
        } else {
            dto.setPhoto(existingProduct.getPhoto());
        }

        dto.setProductId(id);

        productDto updatedProduct = productService.update(id, dto);

        ApiResponse<productDto> response = new ApiResponse<>("success", "Product updated", updatedProduct);
        return ResponseEntity.ok(response);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", e.getMessage()));
    }
}

    

}
