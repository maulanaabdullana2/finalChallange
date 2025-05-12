package com.codeid.eshopay_backend.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.dto.categoryDto;
import com.codeid.eshopay_backend.model.entity.Category;
import com.codeid.eshopay_backend.service.categoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.codeid.eshopay_backend.repository.categoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class categoryServiceImpl implements categoryService {

    private final categoryRepository categoryRepository;

    public static categoryDto mapToDto( Category category ) {
        return new categoryDto(
            category.getCategoryId(),
            category.getCategoryName(),
            category.getCategoryDescription()
            );
    }

    public static Category mapToEntity(categoryDto categoryDto){
        return new Category(
            categoryDto.getCategoryId(),
            categoryDto.getCategoryName(),
            categoryDto.getCategoryDescription()
            );
    }

    @Override
    public List<categoryDto> findAll() {
        log.debug("Request Fetching Data Categories");
        return this.categoryRepository.findAll()
        .stream()
        .map(categoryServiceImpl::mapToDto)
        .collect(Collectors.toList());
    }

    @Override
    public categoryDto findById(Long id) {
       log.debug("Request to get Department : {}", id);

        return this.categoryRepository.findById(id).map(categoryServiceImpl::mapToDto)
            .orElseThrow(()-> new EntityNotFoundException("Category not found with id "+id));
    }

    @Override
    public categoryDto save(categoryDto entity) {
        log.debug("Request to create department : {}", entity);

        return mapToDto(this.categoryRepository
                .save(new Category(entity.getCategoryName(),entity.getCategoryDescription())));   

    }


    @Override
    public categoryDto update(Long id, categoryDto entity) {
        log.debug("Request to update Department : {}", id);

        var category = this.categoryRepository
                            .findById(id)
                            .orElse(null);

        category.setCategoryName(entity.getCategoryName());
        category.setCategoryDescription(entity.getCategoryDescription());
        this.categoryRepository.save(category);
        return mapToDto(category);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);

        var category = this.categoryRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Cannot find Category with id " + id)); 

       this.categoryRepository.delete(category);
    }   
    
}
