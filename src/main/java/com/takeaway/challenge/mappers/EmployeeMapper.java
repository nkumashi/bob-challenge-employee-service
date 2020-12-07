package com.takeaway.challenge.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.takeaway.challenge.dto.EmployeeDTO;
import com.takeaway.challenge.model.Employee;

/**
 * @author Naveen Kumashi
 */

@Component
public class EmployeeMapper {
	public EmployeeDTO mapEntityToDTO(Employee employee) {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeId(employee.getId());
		dto.setEmail(employee.getEmail());
		dto.setFirstName(employee.getFirstName());
		dto.setLastName(employee.getLastName());
		dto.setDob(employee.getDob());
		dto.setDepartmentId(employee.getDepartment().getId());
		dto.setDepartmentName(employee.getDepartment().getName());
		return dto;
	}
	
	public List<EmployeeDTO> mapEntityListToDTOList(Collection<Employee> employeeList) {
        return employeeList.stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }
}
