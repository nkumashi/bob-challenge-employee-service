package com.takeaway.challenge.service;

import java.util.Optional;

import com.takeaway.challenge.dto.DepartmentDTO;
import com.takeaway.challenge.exception.APIError;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.util.DepartmentMapper;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

public class DepartmentServiceImpl implements DepartmentService {
	private DepartmentRepository departmentRepository;
	private DepartmentMapper departmentMapper;
	
	public DepartmentServiceImpl(
			DepartmentRepository departmentRepository,
			DepartmentMapper departmentMapper
	) {
		this.departmentRepository = departmentRepository;
	}
	
	/**
	 * Get department by it's ID
	 * 
	 * @param departmentId
	 * @return Department object if found else error
	 */
	public ResponseWrapper<DepartmentDTO> getById(Long departmentId) {				    	
    	Optional<Department> optional = departmentRepository.findById(departmentId);     
    	if(!optional.isPresent()) {
    		return new ResponseWrapper<>(new APIError(0, "Error", "Department with ID: " + departmentId + " does not exist."));
    	}
    	return new ResponseWrapper<>(departmentMapper.mapEntityToDTO(optional.get()));
	}
}
