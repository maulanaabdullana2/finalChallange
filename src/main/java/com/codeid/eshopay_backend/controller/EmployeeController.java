package com.codeid.eshopay_backend.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeid.eshopay_backend.model.dto.DependentBulkDto;
import com.codeid.eshopay_backend.model.dto.DependentDto;
import com.codeid.eshopay_backend.model.dto.EmployeeDto;
import com.codeid.eshopay_backend.model.enumeration.EnumStatus;
import com.codeid.eshopay_backend.model.response.ApiResponse;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.DependentService;
import com.codeid.eshopay_backend.service.EmployeeService;
import com.codeid.eshopay_backend.service.FileStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employee")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController extends BaseMultipartController<EmployeeDto, Long> {
    private final EmployeeService employeeService;
    private final DependentService dependentService;
    private final FileStorageService fileStorageService;

    @Override
    protected BaseCrudService<EmployeeDto, Long> getService() {
        return employeeService;
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<EmployeeDto> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<?> createMultipart(EmployeeDto dto, MultipartFile file, String description) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload employee photo");
        }

        try {
            String fileName = fileStorageService.storeFileWithRandomName(file);

            dto.setPhoto(fileName);
            var employeeDto = employeeService.save(dto);

            ApiResponse<EmployeeDto> response = new ApiResponse<EmployeeDto>(EnumStatus.Succees.toString(),
                    "Employee created", employeeDto);

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

            // Cek jika file adalah image
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
    public ResponseEntity<?> updateMultipart(Long id, EmployeeDto dto, MultipartFile file, String description) {
        throw new UnsupportedOperationException("Unimplemented method 'updateMultipart'");
    }

    @Override
    public ResponseEntity<EmployeeDto> create(@Valid EmployeeDto entity) {
        return super.create(entity);
    }

  @PostMapping("/dependent/bulk")
public ResponseEntity<?> createBulkDependent(@RequestBody DependentBulkDto dependentBulkDto) {
    List<DependentDto> result = this.dependentService.bulkInsert(dependentBulkDto);

    ApiResponse<List<DependentDto>> response = new ApiResponse<>(
        EnumStatus.Succees.toString(),
        "Dependent created successfully",
        result
    );

    return ResponseEntity.ok(response);
}

   
 @GetMapping("/{id}/dependent")
public ResponseEntity<?> getDependents(@PathVariable Long id) {
    List<DependentDto> dependentsDto = this.dependentService.findDependentByEmployeeId(id);

    ApiResponse<List<DependentDto>> response = new ApiResponse<>(
        EnumStatus.Succees.toString(),
        "Dependent data retrieved successfully",
        dependentsDto
    );

    return ResponseEntity.ok(response);
}

}
