package com.codeid.eshopay_backend.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.dto.shipperDto;
import com.codeid.eshopay_backend.model.entity.Shipper;
import com.codeid.eshopay_backend.repository.shipperRepository;
import com.codeid.eshopay_backend.service.shipperService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class shipperServiceImpl implements shipperService{

     private final shipperRepository shipperRepository;

     
 public static shipperDto mapToDto( Shipper shipper ) {
        return new shipperDto(
            shipper.getShipperId(),
            shipper.getCompanyName(),
            shipper.getPhone()
            );
    }
 @Override
 public List<shipperDto> findAll() {
     log.debug("Request Fetching Data Categories");
        return this.shipperRepository.findAll()
        .stream()
        .map(shipperServiceImpl::mapToDto)
        .collect(Collectors.toList());
 }


 @Override
 public shipperDto findById(Long id) {
    log.debug("Request to get Department : {}", id);

        return this.shipperRepository.findById(id).map(shipperServiceImpl::mapToDto)
            .orElseThrow(()-> new EntityNotFoundException("Category not found with id "+id));
 }


 @Override
 public shipperDto save(shipperDto entity) {
      log.debug("Request to create department : {}", entity);
        return mapToDto(this.shipperRepository
        .save(new Shipper(entity.getCompanyName(),entity.getPhone()))); 
 }

 @Override
 public shipperDto update(Long id, shipperDto entity) {
    log.debug("Request to update Department : {}", id);

    var shipper = this.shipperRepository
                        .findById(id)
                        .orElse(null);

    shipper.setCompanyName(entity.getCompanyName());
    shipper.setPhone(entity.getPhone());
    this.shipperRepository.save(shipper);
    return mapToDto(shipper);
 }

 @Override
 public void delete(Long id) {
    log.debug("Request to delete Department : {}", id);

    var shipper = this.shipperRepository.findById(id)
           .orElseThrow(() -> new EntityNotFoundException("Cannot find Category with id " + id)); 

   this.shipperRepository.delete(shipper);
 }
    
} 
