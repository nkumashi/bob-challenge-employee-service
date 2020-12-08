package com.takeaway.challenge.service;

import java.util.List;

import com.takeaway.challenge.dto.DepartmentDTO;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

public interface DepartmentService {
	/**
	 * Saves the given department object to the database
	 * 
	 * @param department
	 * @return The department object saved
	 */
	public ResponseWrapper<DepartmentDTO> postDepartment(Department department);
	
	/**
	 * Get all departments
	 * 
	 * @return List of all departments found in the database
	 */
	public ResponseWrapper<List<DepartmentDTO>> getAllDepartments();
	
	/**
	 * Get department by it's ID
	 * 
	 * @param departmentId
	 * @return Department object if found else error
	 */
	public ResponseWrapper<DepartmentDTO> getById(Long departmentId);
	
	/**
	 * Update a department by it's id
	 * 
	 * @param id
	 * @param department
	 * @return Updated department object
	 */
	public ResponseWrapper<DepartmentDTO> putEmployee(Department department, Long departmentId);
	
	/**
	 * Delete a department
	 * 
	 * @param department object to be deleted
	 */
	public ResponseWrapper<Object> deleteDepartmentById(Long departmentId);
}
