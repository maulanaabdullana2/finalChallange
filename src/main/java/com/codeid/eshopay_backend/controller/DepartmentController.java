package com.codeid.eshopay_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeid.eshopay_backend.model.dto.DepartmentDto;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/department")
@Slf4j
@RequiredArgsConstructor
public class DepartmentController extends BaseCrudController<DepartmentDto,Long> {
    
    private final DepartmentService departmentService;

    @Override
    protected BaseCrudService<DepartmentDto,Long> getService() {
        return departmentService;
    }

    @Override
    public ResponseEntity<DepartmentDto> create(@Valid DepartmentDto entity) {
        return super.create(entity);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<List<DepartmentDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<DepartmentDto> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<DepartmentDto> update(Long id, @Valid DepartmentDto entity) {
        return super.update(id, entity);
    }

    


    
}