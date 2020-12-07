package com.takeaway.challenge.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.takeaway.challenge.dto.DepartmentDTO;
import com.takeaway.challenge.exception.APIError;
import com.takeaway.challenge.mappers.DepartmentMapper;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {
	private DepartmentRepository departmentRepository;
	private DepartmentMapper departmentMapper;
		
	public DepartmentServiceImpl(
			DepartmentRepository departmentRepository,
			DepartmentMapper departmentMapper
	) {
		this.departmentRepository = departmentRepository;
		this.departmentMapper = departmentMapper;
	}
	
	/**
	 * Saves the given department object to the database
	 * 
	 * @param department
	 * @return The department object saved
	 */
	public ResponseWrapper<DepartmentDTO> postDepartment(Department department) {											
		Department addedDepartment = departmentRepository.save(department);
		return new ResponseWrapper<>(departmentMapper.mapEntityToDTO(addedDepartment));		
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
