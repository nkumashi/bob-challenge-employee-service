package com.takeaway.challenge.service;

import java.util.Collections;
import java.util.List;
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
	 * Get all departments
	 * 
	 * @return List of all departments found in the database
	 */
	public ResponseWrapper<List<DepartmentDTO>> getAllDepartments() {
		List<Department> departmentList = departmentRepository.findAll();
		if(departmentList.isEmpty()) {
			return new ResponseWrapper<>(Collections.emptyList());
		}
        return new ResponseWrapper<>(departmentMapper.mapEntityListToDTOList(departmentList));
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
	
	/**
	 * Update a department by it's id
	 * 
	 * @param id
	 * @param department
	 * @return Updated department object
	 */
	public ResponseWrapper<DepartmentDTO> putEmployee(Department department, Long departmentId) {
		if(!departmentRepository.findById(departmentId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Department with ID: " + departmentId + " does not exist"));
        }				
		
		Department addedDepartment = departmentRepository.save(department);
        return new ResponseWrapper<>(departmentMapper.mapEntityToDTO(addedDepartment));
	}
	
	/**
	 * Delete a department
	 * 
	 * @param department object to be deleted
	 */
	public ResponseWrapper<Object> deleteDepartmentById(Long departmentId) {
		if(!departmentRepository.findById(departmentId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Department with Id " + departmentId + " does not exist"));
        }
		departmentRepository.deleteById(departmentId);

        if(departmentRepository.findById(departmentId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Department with Id " + departmentId + " was not deleted"));
        }

        return new ResponseWrapper<>("Employee with Id " + departmentId + " was deleted");
	}
}
