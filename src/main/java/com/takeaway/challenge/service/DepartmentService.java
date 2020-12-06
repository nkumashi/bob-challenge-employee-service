package com.takeaway.challenge.service;

import com.takeaway.challenge.dto.DepartmentDTO;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

public interface DepartmentService {
	/**
	 * Get department by it's ID
	 * 
	 * @param departmentId
	 * @return Department object if found else error
	 */
	public ResponseWrapper<DepartmentDTO> getById(Long departmentId);
}