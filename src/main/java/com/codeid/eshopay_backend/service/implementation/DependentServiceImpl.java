package com.codeid.eshopay_backend.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.dto.DependentBulkDto;
import com.codeid.eshopay_backend.model.dto.DependentDto;
import com.codeid.eshopay_backend.model.entity.Dependent;
import com.codeid.eshopay_backend.model.entity.Employee;
import com.codeid.eshopay_backend.repository.DependentRepository;
import com.codeid.eshopay_backend.service.DependentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DependentServiceImpl implements DependentService {
    private final DependentRepository dependentRepository;

    //dto
    public static DependentDto mapToDto(Dependent dependent) {
        return DependentDto.builder()
        .dependentId( dependent.getDependentId())
        .firstName( dependent.getFirstName())
        .lastName( dependent.getLastName())
        .relationship( dependent.getRelationship())
        .employeeId(dependent.getEmployee().getEmployeeId())
        .build();
    }

    //entity
    public static Dependent mapToEntity(DependentDto dependentDto) {
        return Dependent.builder()
        .dependentId(dependentDto.getDependentId())
        .firstName(dependentDto.getFirstName())
        .lastName(dependentDto.getLastName())
        .relationship(dependentDto.getRelationship())
        .employee(Employee.builder().employeeId(dependentDto.getEmployeeId()).build())
        .build();
    }


    @Override
    public List<DependentDto> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public DependentDto findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public DependentDto save(DependentDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public DependentDto update(Long id, DependentDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

     @Override
    public List<DependentDto> findDependentByEmployeeId(Long id) {
        var result = this.dependentRepository.findDependentByEmplooyeeIdJpql(id)
                        .stream()
                        .map(DependentServiceImpl::mapToDto)
                        .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<DependentDto> bulkInsert(DependentBulkDto dependentBulkDto) {
         var dependentListDto = dependentBulkDto.getDependents();

       //1. old school
       /* List<Dependent> dependentEntities = new ArrayList<>();

       for (DependentDto dto : dependentListDto) {
            dependentEntities.add(DependentServiceImpl.mapToEntity(dto));
       } */

       //2. cara functional programming then convert to entity
       List<Dependent> dependentEntities = dependentListDto.stream()
                                            .map(DependentServiceImpl::mapToEntity)
                                            .collect(Collectors.toList());
      
       var entitiesSaved = this.dependentRepository.saveAll(dependentEntities);      
       return entitiesSaved.stream().map(DependentServiceImpl::mapToDto)
                .collect(Collectors.toList());               
    }
    
   
    
}
