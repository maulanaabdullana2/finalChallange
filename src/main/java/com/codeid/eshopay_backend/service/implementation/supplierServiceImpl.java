package com.codeid.eshopay_backend.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.entity.Supplier;
import com.codeid.eshopay_backend.model.dto.supplierDto;
import com.codeid.eshopay_backend.repository.supplierRepository;
import com.codeid.eshopay_backend.service.supplierService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Slf4j
@Service
@RequiredArgsConstructor
public class supplierServiceImpl implements supplierService {

     private final supplierRepository supplierRepository;

     public static supplierDto mapDto(Supplier supplier){
        return new supplierDto(
            supplier.getSupplierId(),
            supplier.getCompanyName()
            );
     }

     public static Supplier mapToEntity(supplierDto supplierDto){
        return new Supplier(
            supplierDto.getSupplierId(),
            supplierDto.getCompanyName()
            );
    }


     
    @Override
    public List<supplierDto> findAll() {
         log.debug("Request Fetching Data Categories");
        return this.supplierRepository.findAll()
        .stream()
        .map(supplierServiceImpl::mapDto)
        .collect(Collectors.toList());
    }

    @Override
    public supplierDto findById(Long id) {
        log.debug("Request to get Department : {}", id);

        return this.supplierRepository.findById(id).map(supplierServiceImpl::mapDto)
            .orElseThrow(()-> new EntityNotFoundException("Supplier not found with id "+id));
    }

    @Override
    public supplierDto save(supplierDto entity) {
        log.debug("Request to create department : {}", entity);
        return mapDto(this.supplierRepository
        .save(new Supplier(entity.getCompanyName()))); 
    }

    @Override
    public supplierDto update(Long id, supplierDto entity) {
        log.debug("Request to update Department : {}", id);

        var supplier = this.supplierRepository
                            .findById(id)
                            .orElse(null);

        supplier.setCompanyName(entity.getCompanyName());
        this.supplierRepository.save(supplier);
        return mapDto(supplier);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);

        var supplier = this.supplierRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Cannot find Category with id " + id)); 
    
       this.supplierRepository.delete(supplier);
    }
    
}
