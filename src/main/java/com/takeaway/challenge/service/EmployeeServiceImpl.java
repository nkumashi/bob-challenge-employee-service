package com.takeaway.challenge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.exception.APIError;
import com.takeaway.challenge.hateos.assembler.EmployeeModelAssembler;
import com.takeaway.challenge.hateos.model.EmployeeModel;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeRepository employeeRepository;
	private DepartmentRepository departmentRepository;
    private EntityManager entityManager;
    private EmployeeModelAssembler employeeModelAssembler;

	public EmployeeServiceImpl(
			EmployeeRepository employeeRepository,
			DepartmentRepository departmentRepository,
			EntityManager entityManager,
			EmployeeModelAssembler employeeModelAssembler
	) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.entityManager = entityManager;
		this.employeeModelAssembler = employeeModelAssembler;
	}

	/**
	 * Saves the given employee object to the database
	 * 
	 * @param employee
	 * @return The employee object saved
	 */
	public ResponseWrapper<EmployeeModel> postEmployee(Employee employee) {																
		Long departmentId = employee.getDepartment().getId();
		Optional<Department> optional = departmentRepository.findById(departmentId);
		if(!optional.isPresent()) {
			return new ResponseWrapper<>(new APIError(0, "Error", "Department with ID: " + departmentId + " does not exist."));
		}

		List<Employee> employeesByEmail = employeeRepository.findByEmail(employee.getEmail());
		if(employeesByEmail.size() > 0) {
			return new ResponseWrapper<>(new APIError(0, "Error", "Employee with email: " + employee.getEmail() + " already exist."));
		}
		
		employee.setDepartment(optional.get());
		Employee addedEmployee = employeeRepository.save(employee);
		return new ResponseWrapper<>(employeeModelAssembler.toModel(addedEmployee));		
	}

	/**
	 * Get all employees
	 * 
	 * @return List of all employees found in the database
	 */
	public ResponseWrapper<CollectionModel<EmployeeModel>> getAllEmployees() {
		List<Employee> employeeList = employeeRepository.findAll();
        return new ResponseWrapper<>(employeeModelAssembler.toCollectionModel(employeeList));
	}
	
	/**
	 * Get all employees by provided page criteria
	 * 
	 * @return List of all employees by page found in the database
	 */
	public ResponseWrapper<CollectionModel<EmployeeModel>> getPagedEmployees(Pageable page) {
		List<Employee> employeeList = new ArrayList<Employee>();
		Page<Employee> pages = employeeRepository.findAll(page);
		employeeList = pages.getContent();
        return new ResponseWrapper<>(employeeModelAssembler.toCollectionModel(employeeList));
	}
	
	/**
	 * Get all employees by provided limit
	 * 
	 * @return List of all employees by limit found in the database
	 */
	public ResponseWrapper<CollectionModel<EmployeeModel>> getEmployeesByLimit(int limit) {
		List<Employee> employeeList = entityManager.createQuery("SELECT e FROM Employee e ORDER BY e.firstName",
				Employee.class).setMaxResults(limit).getResultList();
        return new ResponseWrapper<>(employeeModelAssembler.toCollectionModel(employeeList));
	}
	
	/**
	 * Get employee by it's UUID
	 * 
	 * @param employeeUuid
	 * @return Employee object if found else error
	 */
	public ResponseWrapper<EmployeeModel> getEmployeeById(UUID employeeId) {
		Optional<Employee> optional = employeeRepository.findById(employeeId);
		if(!optional.isPresent()) {
			return new ResponseWrapper<>(new APIError(0, "Error", "Employee with ID: " + employeeId + " does not exist."));
		}
		return new ResponseWrapper<>(employeeModelAssembler.toModel(optional.get()));
	}

	/**
	 * Update an employee by it's UUID
	 * 
	 * @param uuid
	 * @param employee
	 * @return Updated employee object
	 */
	public ResponseWrapper<EmployeeModel> putEmployee(Employee employee, UUID employeeId) {
		if(!employeeRepository.findById(employeeId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Employee with ID: " + employeeId + " does not exist"));
        }		
		
		List<Employee> employeesByEmail = employeeRepository.findByEmail(employee.getEmail());
		if(employeesByEmail.size() > 0) {
			return new ResponseWrapper<>(new APIError(0, "Error", "Employee with email: " + employee.getEmail() + " already exist."));
		}
		
        Long departmentId = employee.getDepartment().getId();
        if(!departmentRepository.findById(departmentId).isPresent()) {
            return new ResponseWrapper<>(new APIError(0, "Error", "Department with ID: " + departmentId + " does not exist"));
        }
        employee.setId(employeeId);
        Employee addedEmployee = employeeRepository.save(employee);

        return new ResponseWrapper<>(employeeModelAssembler.toModel(addedEmployee));
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
