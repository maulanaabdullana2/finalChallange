package com.codeid.eshopay_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeid.eshopay_backend.model.dto.shipperDto;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.shipperService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/shipper/")
@Slf4j
@RequiredArgsConstructor
public class shipperController  extends BaseCrudController<shipperDto,Long> {
       private final shipperService shipperService;

       @Override
       protected BaseCrudService<shipperDto, Long> getService() {
            return shipperService;
       }

       @Override
       public ResponseEntity<shipperDto> create(@Valid shipperDto entity) {
        return super.create(entity);
       }

       @Override
       public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
       }

       @Override
       public ResponseEntity<List<shipperDto>> getAll() {
        return super.getAll();
       }

       @Override
       public ResponseEntity<shipperDto> getById(Long id) {
        return super.getById(id);
       }

       @Override
       public ResponseEntity<shipperDto> update(Long id, @Valid shipperDto entity) {
        return super.update(id, entity);
       }

       
       
}
