package com.takeaway.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.exception.APIError;
import com.takeaway.challenge.hateos.assembler.DepartmentModelAssembler;
import com.takeaway.challenge.hateos.model.DepartmentModel;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {
	private EmployeeRepository employeeRepository;
	private DepartmentRepository departmentRepository;
	private DepartmentModelAssembler departmentModelAssembler;
		
	public DepartmentServiceImpl(
			EmployeeRepository employeeRepository,
			DepartmentRepository departmentRepository,
			DepartmentModelAssembler departmentModelAssembler
	) {
		this.departmentRepository = departmentRepository;
		this.departmentModelAssembler= departmentModelAssembler;
		this.employeeRepository = employeeRepository;
	}
	
	/**
	 * Saves the given department object to the database
	 * 
	 * @param department
	 * @return The department object saved
	 */
	public ResponseWrapper<DepartmentModel> postDepartment(Department department) {											
		Department addedDepartment = departmentRepository.save(department);
		return new ResponseWrapper<>(departmentModelAssembler.toModel(addedDepartment));		
	}
	
	/**
	 * Get all departments
	 * 
	 * @return List of all departments found in the database
	 */
	public ResponseWrapper<CollectionModel<DepartmentModel>> getAllDepartments() {
		List<Department> departmentList = departmentRepository.findAll();
        return new ResponseWrapper<>(departmentModelAssembler.toCollectionModel(departmentList));
	}
	
	/**
	 * Get department by it's ID
	 * 
	 * @param departmentId
	 * @return Department object if found else error
	 */
	public ResponseWrapper<DepartmentModel> getDepartmentById(Long departmentId) {				    	
    	Optional<Department> optional = departmentRepository.findById(departmentId);     
    	if(!optional.isPresent()) {
    		return new ResponseWrapper<>(new APIError(0, "Error", "Department with ID: " + departmentId + " does not exist."));
    	}
    	return new ResponseWrapper<>(departmentModelAssembler.toModel(optional.get()));
	}
	
	/**
	 * Update a department by it's id
	 * 
	 * @param id
	 * @param department
	 * @return Updated department object
	 */
	public ResponseWrapper<DepartmentModel> putDepartment(Department department) {
		if(!departmentRepository.findById(department.getId()).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Department with ID: " + department.getId() + " does not exist"));
        }				
		
		Department addedDepartment = departmentRepository.save(department);
        return new ResponseWrapper<>(departmentModelAssembler.toModel(addedDepartment));
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
		employeeRepository.deleteAll();
		departmentRepository.deleteById(departmentId);

        if(departmentRepository.findById(departmentId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Department with Id " + departmentId + " was not deleted"));
        }

        return new ResponseWrapper<>("Employee with Id " + departmentId + " was deleted");
	}
}
