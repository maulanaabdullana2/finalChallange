package com.codeid.eshopay_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeid.eshopay_backend.model.dto.categoryDto;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.categoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/category/")
@Slf4j
@RequiredArgsConstructor
public class categoryController extends BaseCrudController<categoryDto,Long> {
    private final categoryService categoryService;

    @Override
    protected BaseCrudService<categoryDto, Long> getService() {
        return categoryService;
    }

    @Override
    public ResponseEntity<categoryDto> create(@Valid categoryDto entity) {
        return super.create(entity);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<List<categoryDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<categoryDto> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<categoryDto> update(Long id, @Valid categoryDto entity) {
        return super.update(id, entity);
    }

    
}
