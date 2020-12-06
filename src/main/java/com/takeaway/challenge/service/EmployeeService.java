package com.takeaway.challenge.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.takeaway.challenge.dto.EmployeeDTO;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

public interface EmployeeService {
	/**
	 * Saves the given employee object to the database
	 * 
	 * @param employee
	 * @return The employee object saved
	 */
	public ResponseWrapper<EmployeeDTO> postEmployee(Employee employee);
	
	/**
	 * Get all employees
	 * 
	 * @return List of all employees found in the database
	 */
	public ResponseWrapper<List<EmployeeDTO>> getAllEmployees();
	
	/**
	 * Get all employees by provided page criteria
	 * 
	 * @return List of all employees by page found in the database
	 */
	public ResponseWrapper<List<EmployeeDTO>> getPagedEmployees(Pageable page);
	
	/**
	 * Get all employees by provided limit
	 * 
	 * @return List of all employees by limit found in the database
	 */
	public ResponseWrapper<List<EmployeeDTO>> getEmployeesByLimit(int limit);
	
	/**
	 * Get employee by it's UUID
	 * 
	 * @param employeeUuid
	 * @return Employee object if found else error
	 */
	public ResponseWrapper<EmployeeDTO> getByUuid(UUID employeeUuid);
	
	/**
	 * Update an employee by it's UUID
	 * 
	 * @param uuid
	 * @param employee
	 * @return Updated employee object
	 */
	public ResponseWrapper<EmployeeDTO> putEmployee(Employee employee, UUID uuid);
		
	/**
	 * Delete an employee
	 * 
	 * @param employee object to be deleted
	 */
	public ResponseWrapper<Object> deleteEmployeeById(UUID employeeId);
}
