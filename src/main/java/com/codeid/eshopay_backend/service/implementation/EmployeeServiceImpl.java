package com.codeid.eshopay_backend.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.dto.EmployeeDto;
import com.codeid.eshopay_backend.model.entity.Department;
import com.codeid.eshopay_backend.model.entity.Employee;
import com.codeid.eshopay_backend.repository.EmployeeRepository;
import com.codeid.eshopay_backend.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public static EmployeeDto mapToDto(Employee employee) {
        return new EmployeeDto(
                employee.getEmployeeId(), employee.getFirstName(),
                employee.getLastName(), employee.getEmail(),
                employee.getPhoneNumber(), employee.getHireDate(),
                employee.getJobId(), employee.getSalary(),
                employee.getManagerId(), employee.getPhoto(),
                DepartmentServiceImpl.mapToDto(employee.getDepartment())
                );
    }

    private  Employee mapToEntity(EmployeeDto employeeDto) {
        return new Employee(employeeDto.getEmployeeId(),employeeDto.getFirstName(),employeeDto.getLastName(),
                            employeeDto.getEmail(),employeeDto.getPhoneNumber(),employeeDto.getHireDate(),
                            employeeDto.getJobId(),employeeDto.getSalary(),employeeDto.getManagerId(),employeeDto.getPhoto(),
                            DepartmentServiceImpl.mapToEntity(employeeDto.getDepartment()));

    }

    @Override
    public List<EmployeeDto> findAll() {
        log.debug("Request to get all Employees");
        return this.employeeRepository.findAll().stream()
                .map(EmployeeServiceImpl::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto findById(Long id) {
          log.debug("Request to get Employee : {}", id);
        return this.employeeRepository.findById(id).map(EmployeeServiceImpl::mapToDto)
            .orElseThrow(()-> new EntityNotFoundException("Employee not found with id "+id));
    }

    @Override
    public EmployeeDto save(EmployeeDto entity) {
        log.debug("Request to create Employee : {}", entity);

        var employee = mapToEntity(entity);

        //after save, langsung ubah ke dto
        return mapToDto(this.employeeRepository.save(employee));  
    }

    @Override
    public EmployeeDto update(Long id, EmployeeDto entity) {
        log.debug("Request to update Employee : {}", id);
        var employee = this.employeeRepository
                            .findById(id)
                            .orElseThrow(()-> new EntityNotFoundException("Employee not found with id "+id));

        employee.setFirstName(entity.getFirstName());
        employee.setLastName(entity.getLastName());
        employee.setEmail(entity.getEmail());
        employee.setHireDate(entity.getHireDate());
        employee.setSalary(entity.getSalary());
        employee.setJobId(entity.getJobId());
        employee.setManagerId(entity.getManagerId());
        employee.setDepartment(new Department(entity.getDepartment().getDepartmentId(), entity.getDepartment().getDepartmentName()));

        this.employeeRepository.save(employee);

        return mapToDto(employee);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete employee : {}", id);

        var employee = this.employeeRepository.findById(id)
        .orElseThrow(()-> new EntityNotFoundException("Employee not found with id "+id));

       this.employeeRepository.deleteById(employee.getEmployeeId());
    }
    
}