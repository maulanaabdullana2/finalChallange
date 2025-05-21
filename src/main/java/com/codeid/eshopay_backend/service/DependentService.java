package com.codeid.eshopay_backend.service;

import java.util.List;

import com.codeid.eshopay_backend.model.dto.DependentBulkDto;
import com.codeid.eshopay_backend.model.dto.DependentDto;

public interface DependentService extends BaseCrudService<DependentDto,Long>{
    List<DependentDto> findDependentByEmployeeId(Long id);
    List<DependentDto> bulkInsert(DependentBulkDto dependentBulkDto);
}
