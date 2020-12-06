package com.takeaway.challenge.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import com.takeaway.challenge.dto.EmployeeDTO;
import com.takeaway.challenge.exception.APIError;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.util.EmployeeMapper;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeRepository employeeRepository;
	private DepartmentRepository departmentRepository;
	private EmployeeMapper employeeMapper;
	//@PersistenceContext
    private EntityManager entityManager;

	public EmployeeServiceImpl(
			EmployeeRepository employeeRepository,
			DepartmentRepository departmentRepository,
			EmployeeMapper employeeMapper,
			EntityManager entityManager) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.employeeMapper = employeeMapper;
		this.entityManager = entityManager;
	}

	/**
	 * Saves the given employee object to the database
	 * 
	 * @param employee
	 * @return The employee object saved
	 */
	public ResponseWrapper<EmployeeDTO> postEmployee(Employee employee) {				
		if(!ObjectUtils.isEmpty(employee.getId())) {
			return new ResponseWrapper<>(new APIError(0, "Error", "Employee's ID is set automatically, do not try to set it."));
		}				
		
		if(ObjectUtils.isEmpty(employee.getDepartment())) {
			return new ResponseWrapper<>(new APIError(0, "Error", "Department object cannot be null."));
		}
		
		Long departmentId = employee.getDepartment().getId();
		Optional<Department> optional = departmentRepository.findById(departmentId);
		if(!optional.isPresent()) {
			return new ResponseWrapper<>(new APIError(0, "Error", "Department with ID: " + departmentId + " does not exist."));
		}

		employee.setDepartment(optional.get());
		Employee addedEmployee = employeeRepository.save(employee);
		return new ResponseWrapper<>(employeeMapper.mapEntityToDTO(addedEmployee));		
	}

	/**
	 * Get all employees
	 * 
	 * @return List of all employees found in the database
	 */
	public ResponseWrapper<List<EmployeeDTO>> getAllEmployees() {
		List<Employee> employeeList = employeeRepository.findAll();
		if(employeeList.isEmpty()) {
			return new ResponseWrapper<>(Collections.emptyList());
		}
        return new ResponseWrapper<>(employeeMapper.mapEntityListToDTOList(employeeList));
	}
	
	/**
	 * Get all employees by provided page criteria
	 * 
	 * @return List of all employees by page found in the database
	 */
	public ResponseWrapper<List<EmployeeDTO>> getPagedEmployees(Pageable page) {
		List<Employee> employeeList = new ArrayList<Employee>();
		Page<Employee> pages = employeeRepository.findAll(page);
		employeeList = pages.getContent();
		if(employeeList.isEmpty()) {
			return new ResponseWrapper<>(Collections.emptyList());
		}
        return new ResponseWrapper<>(employeeMapper.mapEntityListToDTOList(employeeList));
	}
	
	/**
	 * Get all employees by provided limit
	 * 
	 * @return List of all employees by limit found in the database
	 */
	public ResponseWrapper<List<EmployeeDTO>> getEmployeesByLimit(int limit) {
		List<Employee> employeeList = entityManager.createQuery("SELECT e FROM Employee e ORDER BY e.firstName",
				Employee.class).setMaxResults(limit).getResultList();
		if(employeeList.isEmpty()) {
			return new ResponseWrapper<>(Collections.emptyList());
		}
        return new ResponseWrapper<>(employeeMapper.mapEntityListToDTOList(employeeList));
	}
	
	/**
	 * Get employee by it's UUID
	 * 
	 * @param employeeUuid
	 * @return Employee object if found else error
	 */
	public ResponseWrapper<EmployeeDTO> getByUuid(UUID employeeId) {
		Optional<Employee> optional = employeeRepository.findById(employeeId);
		if(!optional.isPresent()) {
			return new ResponseWrapper<>(new APIError(0, "Error", "Employee with ID: " + employeeId + " does not exist."));
		}
		return new ResponseWrapper<>(employeeMapper.mapEntityToDTO(optional.get()));
	}

	/**
	 * Update an employee by it's UUID
	 * 
	 * @param uuid
	 * @param employee
	 * @return Updated employee object
	 */
	public ResponseWrapper<EmployeeDTO> putEmployee(Employee employee, UUID employeeId) {
		if(!employeeRepository.findById(employeeId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Employee with ID: " + employeeId + " does not exist"));
        }

		if(ObjectUtils.isEmpty(employee.getDepartment())) {
			return new ResponseWrapper<>(new APIError(0, "Error", "Department object cannot be null."));
		}
		
        Long departmentId = employee.getDepartment().getId();
        if(!departmentRepository.findById(departmentId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Department with ID: " + departmentId + " does not exist"));
        }
        employee.setId(employeeId);
        Employee addedEmployee = employeeRepository.save(employee);

        return new ResponseWrapper<>(employeeMapper.mapEntityToDTO(addedEmployee));
	}
	
	/**
	 * Delete an employee
	 * 
	 * @param employee object to be deleted
	 */
	public ResponseWrapper<Object> deleteEmployeeById(UUID employeeId) {
		if(!employeeRepository.findById(employeeId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Employee with Id " + employeeId + " does not exist"));
        }
        employeeRepository.deleteById(employeeId);

        if(employeeRepository.findById(employeeId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Employee with Id " + employeeId + " was not deleted"));
        }

        return new ResponseWrapper<>("Employee with Id " + employeeId + " was deleted");
	}
}
