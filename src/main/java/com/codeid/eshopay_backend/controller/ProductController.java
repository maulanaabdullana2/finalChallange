package com.codeid.eshopay_backend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeid.eshopay_backend.model.dto.ProductImageDto;
import com.codeid.eshopay_backend.model.dto.productDto;
import com.codeid.eshopay_backend.model.dto.supplierDto;
import com.codeid.eshopay_backend.model.enumeration.EnumStatus;
import com.codeid.eshopay_backend.model.response.ApiResponse;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.FileStorageService;
import com.codeid.eshopay_backend.service.productService;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get All Department", description = "Fecth all departmetns")
    public ResponseEntity getAll() {
        List<productDto> supplierList = super.getAll().getBody();
        try {
            ApiResponse<List<productDto>> response = new ApiResponse<>(
                    EnumStatus.Succees.toString(),
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
            if (file != null && !file.isEmpty()) {
                String newFileName = fileStorageService.storeFileWithRandomName(file);
                dto.setPhoto(newFileName);
            } else {
                dto.setPhoto(existingProduct.getPhoto());
            }

            dto.setProductId(id);

            productDto updatedProduct = productService.update(id, dto);

            ApiResponse<productDto> response = new ApiResponse<>(EnumStatus.Succees.toString(), "Product updated",
                    updatedProduct);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/bulk")
    public ResponseEntity<List<ProductImageDto>> bulkFindAll(@PathVariable Long id) {
        List<ProductImageDto> productImages = productService.bulkFindAll(id);
        return new ResponseEntity<>(productImages, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/bulk", consumes = "multipart/form-data")
    public ResponseEntity<?> createMultipartBulk(@PathVariable Long id,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {
        if (files.length == 0) {
            return ResponseEntity.badRequest().body("Please upload product images");
        }

        try {

            List<String> filenames = new ArrayList<>();

            for (var file : files) {
                String filename = fileStorageService.storeFileWithRandomName(file);

                filenames.add(filename);
            }

            List<ProductImageDto> productImagesDto = productService.bulkCreate(id, files, filenames);

            ApiResponse<List<ProductImageDto>> response = new ApiResponse<>(
                    EnumStatus.Succees.toString(),
                    "Product images uploaded successfully",
                    productImagesDto);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonMap("error", ex.getMessage()));
        }
    }
    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<?> deleteSingleImage(
            @PathVariable Long productId,
            @PathVariable Long imageId) {
        try {
            productService.deleteImage(productId, imageId);
            return ResponseEntity.ok(new ApiResponse<>(
                    EnumStatus.Succees.toString(),
                    "Product image deleted successfully",
                    null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", ex.getMessage()));
        }
    }

}
