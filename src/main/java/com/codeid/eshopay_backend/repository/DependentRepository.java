package com.codeid.eshopay_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codeid.eshopay_backend.model.entity.Dependent;

public interface DependentRepository extends JpaRepository<Dependent,Long>{
     @Query("SELECT d FROM Dependent d WHERE d.employee.id = :id")
     List<Dependent> findDependentByEmplooyeeIdJpql(@Param("id") Long id);
} 
