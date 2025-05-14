package com.codeid.eshopay_backend.service.implementation;


import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.dto.DepartmentDto;
import com.codeid.eshopay_backend.model.entity.Department;
import com.codeid.eshopay_backend.repository.DepartmentRepository;
import com.codeid.eshopay_backend.service.DepartmentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    
    private final DepartmentRepository departmentRepository;

    public static DepartmentDto mapToDto(Department department){
        return new DepartmentDto(
            department.getDepartmentId(), 
            department.getDepartmentName());
    }

    public static Department mapToEntity(DepartmentDto departmentDto){
        return new Department(
            departmentDto.getDepartmentId(), 
            departmentDto.getDepartmentName()
            );
    }

    @Override
    public List<DepartmentDto> findAll() {
        log.debug("request fetching data departments");
        return this.departmentRepository.findAll()
                .stream()
                .map(DepartmentServiceImpl::mapToDto) // untuk ubah tipe data dari department ke departmentDto
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto findById(Long id) {
        log.debug("Request to get Department : {}", id);

        return this.departmentRepository.findById(id).map(DepartmentServiceImpl::mapToDto)
            .orElseThrow(()-> new EntityNotFoundException("Department not found with id "+id));
    }

    @Override
    public DepartmentDto save(DepartmentDto entity) {
         log.debug("Request to create department : {}", entity);

        return mapToDto(this.departmentRepository
                .save(new Department(entity.getDepartmentName())));   
    }

    @Override
    public DepartmentDto update(Long id, DepartmentDto entity) {
        log.debug("Request to update Department : {}", id);

        var department = this.departmentRepository
                            .findById(id)
                            .orElse(null);

        department.setDepartmentName(entity.getDepartmentName());
        this.departmentRepository.save(department);
        return mapToDto(department);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);

         var department = this.departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Department with id " + id)); 

        this.departmentRepository.delete(department);
    }

    
    
}