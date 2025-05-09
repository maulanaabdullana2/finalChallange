package com.codeid.eshopay_backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codeid.eshopay_backend.service.implementation.categoryServiceImpl;
import com.codeid.eshopay_backend.repository.categoryRepository;
import com.codeid.eshopay_backend.model.dto.categoryDto;
import com.codeid.eshopay_backend.model.entity.Category;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
            new Category(1L, "Beverages", "Soft drinks, coffees, teas, beers, and ales")
        );
       
        when(categoryRepository.findAll()).thenReturn(mockCategories);
    
        List<categoryDto> result = categoryService.findAll();
    
        assertEquals(1, result.size());
        assertEquals("Beverages", result.get(0).getCategoryName());
    
        verify(categoryRepository, times(1)).findAll();
    }
    
    
}
