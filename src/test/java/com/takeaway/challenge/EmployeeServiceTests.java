package com.takeaway.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.takeaway.challenge.hateos.assembler.EmployeeModelAssembler;
import com.takeaway.challenge.hateos.model.EmployeeModel;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.service.EmployeeServiceImpl;
import com.takeaway.challenge.service.KafkaProducerService;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {		
	@Mock
    private EntityManager entityManager;
	
	@Mock
	private KafkaProducerService producerService;
    
	@Mock
    private EmployeeRepository employeeRepository;		
	
	@Mock
	EmployeeModelAssembler employeeModelAssembler;
	
	@Mock 
	private DepartmentRepository departmenteRepository;		
	
	@Mock
	private Employee mockedEmployee;
	
	@Mock
	private EmployeeModel mockedEmployeeModel;
	
	@Mock
    private Department mockedDepartment;
	
	@InjectMocks
    private EmployeeServiceImpl employeeService;
	
	@Test
	public void should_create_an_employee() {				
		UUID uuid = UUID.randomUUID();		       
        Department department = new Department("Dummy");
		department.setId(1l);
		
		LocalDate dob = LocalDate.now();
		Employee employee = new Employee("dummy1@email.com", "First name", "Last name", dob, department);
		employee.setId(uuid);
		
        when(departmenteRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        
        employeeService.postEmployee(employee);
        verify(employeeRepository).save(any());
        verify(employeeModelAssembler, times(1)).toModel(any());	
	}
	
	@Test
	public void should_fail_creating_employee_provided_invalid_department_id() {	
		Department department = new Department("Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee employee = new Employee("dummy@email.com", "First name", "Last name", dob, department);
		        
        ResponseWrapper<EmployeeModel> response = employeeService.postEmployee(employee);
        assertNull(response.getData());
        assertNotNull(response.getError());
	}
	
	@Test
	public void should_fetch_all_employees() {
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(mockedEmployee);
		when(employeeRepository.findAll()).thenReturn(employeeList);
		
		employeeService.getAllEmployees();
		verify(employeeRepository).findAll();
		verify(employeeModelAssembler, times(1)).toCollectionModel(anyList());	
	}
	
	@Test
	public void should_fetch_an_employee_by_uuid() {
		UUID uuid = UUID.randomUUID();
		Department department = new Department("Dummy");
		department.setId(1l);
		
		LocalDate dob = LocalDate.now();
		Employee employee = new Employee("dummy@email.com", "First name", "Last name", dob, department);
		employee.setId(uuid);
		when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
		when(employeeModelAssembler.toModel(any())).thenReturn(mockedEmployeeModel);
		
		ResponseWrapper<EmployeeModel> response = employeeService.getEmployeeById(uuid);
        assertNotNull(response.getData());
        assertNull(response.getError());
        assertEquals(mockedEmployeeModel, response.getData());
	}
	
	@Test
	public void should_update_an_employee_by_uuid() {
		UUID uuid = UUID.randomUUID();		       
        Department department = new Department("Dummy");
		department.setId(1l);
		
		LocalDate dob = LocalDate.now();
		Employee employee = new Employee("dummy1@email.com", "First name", "Last name", dob, department);
		employee.setId(uuid);
		
		when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        when(departmenteRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        
        employeeService.putEmployee(employee);
        verify(employeeRepository).save(any());
        verify(employeeModelAssembler, times(1)).toModel(any());	 
	}
}
