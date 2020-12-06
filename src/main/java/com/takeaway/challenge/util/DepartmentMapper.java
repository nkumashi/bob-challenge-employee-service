package com.takeaway.challenge.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.takeaway.challenge.dto.DepartmentDTO;
import com.takeaway.challenge.model.Department;

@Component
public class DepartmentMapper {
	public DepartmentDTO mapEntityToDTO(Department department) {
		DepartmentDTO dto = new DepartmentDTO();
		dto.setDepartmentId(department.getId());
		dto.setDepartmentName(department.getName());		
		return dto;
	}
	
	public List<DepartmentDTO> mapEntityListToDTOList(Collection<Department> departmentList) {
        return departmentList.stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }
}
