package com.codeid.eshopay_backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codeid.eshopay_backend.service.implementation.categoryServiceImpl;

import jakarta.persistence.EntityNotFoundException;

import com.codeid.eshopay_backend.repository.categoryRepository;
import com.codeid.eshopay_backend.model.dto.categoryDto;
import com.codeid.eshopay_backend.model.entity.Category;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryTest {

    @Mock
    private categoryRepository categoryRepository;

    @InjectMocks
    private categoryServiceImpl categoryService;

    @Test
    void testGetAllCategories() {
        List<Category> mockCategories = List.of(
                new Category(1L, "Beverages", "Soft drinks, coffees, teas, beers, and ales"));

        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<categoryDto> result = categoryService.findAll();

        assertEquals(1, result.size());
        assertEquals("Beverages", result.get(0).getCategoryName());
    }

    @Test
    void testCreateCategory() {
        var category = new Category(null, "Beverages", "Minuman");
        var savedCategory = new Category(1L, "Beverages", "Minuman");

        when(categoryRepository.save(category)).thenReturn(savedCategory);
        var inputDto = categoryServiceImpl.mapToDto(category);
        var result = categoryService.save(inputDto);

        // ass
        assertEquals(1L, result.getCategoryId());
        assertEquals("Beverages", result.getCategoryName());
    }

    @Test
    void testUpdateCategory() {
        var existingCategory = new Category(1L, "Beverages", "Minuman Lama");
        var updateDto = new categoryDto(1L, "Beverages", "Minuman Baru");
        var updatedCategory = new Category(1L, "Beverages", "Minuman Baru");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);
        
        var result = categoryService.update(1L, updateDto);

        assertEquals("Minuman Baru", result.getCategoryDescription());
        assertEquals(1L, result.getCategoryId());
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category(1L, "Beverages", "Soft drinks, coffees, teas, beers, and ales");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        var result = categoryService.findById(1L);

        assertEquals(1L, result.getCategoryId());
        assertEquals("Beverages", result.getCategoryName());
    }

    @Test
    void testGetCategoryById_not_found_throwsException() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.findById(99L);
        });

        assertEquals("Category not found with id 99", ex.getMessage());
    }

    @Test
    void testDeleteCategory() {
        Category category = new Category(1L, "Beverages", "Minuman");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);

        categoryService.delete(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).delete(category);
    }

}
