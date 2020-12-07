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

import com.takeaway.challenge.dto.EmployeeDTO;
import com.takeaway.challenge.mappers.EmployeeMapper;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.service.EmployeeServiceImpl;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {		
	@Mock
    private EntityManager entityManager;
    
	@Mock
    private EmployeeRepository employeeRepository;		
	
	@Mock
	private EmployeeMapper employeeMapper;
	
	@Mock 
	private DepartmentRepository departmenteRepository;		
	
	@Mock
	private Employee mockedEmployee;
	
	@Mock
	private EmployeeDTO mockedEmployeeDTO;
	
	@Mock
    private Department mockedDepartment;
	
	@InjectMocks
    private EmployeeServiceImpl employeeService;
	
	@Test
	public void should_create_an_employee() {		
		Department department = new Department("Dummy");
		department.setId(1l);
		
		LocalDate dob = LocalDate.now();
		Employee employee = new Employee("dummy@email.com", "First name", "Last name", dob, department);
		
		when(employeeRepository.save(any())).thenReturn(employee);
        when(departmenteRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(employeeMapper.mapEntityToDTO(any())).thenReturn(mockedEmployeeDTO);
        
        ResponseWrapper<EmployeeDTO> response = employeeService.postEmployee(employee);
        assertNotNull(response.getData());
        assertNull(response.getError());
        assertEquals(mockedEmployeeDTO, response.getData());       
	}
	
	@Test
	public void should_fail_creating_employee_provided_null_department_object() {				
		LocalDate dob = LocalDate.now();
		Employee employee = new Employee("dummy@email.com", "First name", "Last name", dob, null);
		        
        ResponseWrapper<EmployeeDTO> response = employeeService.postEmployee(employee);
        assertNull(response.getData());
        assertNotNull(response.getError());
	}
	
	@Test
	public void should_fail_creating_employee_provided_invalid_department_id() {	
		Department department = new Department("Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee employee = new Employee("dummy@email.com", "First name", "Last name", dob, department);
		        
        ResponseWrapper<EmployeeDTO> response = employeeService.postEmployee(employee);
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
		verify(employeeMapper, times(1)).mapEntityListToDTOList(anyList());	
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
		when(employeeMapper.mapEntityToDTO(any())).thenReturn(mockedEmployeeDTO);
		
		ResponseWrapper<EmployeeDTO> response = employeeService.getByUuid(uuid);
        assertNotNull(response.getData());
        assertNull(response.getError());
        assertEquals(mockedEmployeeDTO, response.getData());
	}
	
	@Test
	public void should_update_an_employee_by_uuid() {
		UUID uuid = UUID.randomUUID();		       
        Department department = new Department("Dummy");
		department.setId(1l);
		
		LocalDate dob = LocalDate.now();
		Employee employee = new Employee("dummy@email.com", "First name", "Last name", dob, department);
		employee.setId(uuid);
		
		when(employeeRepository.findById(any())).thenReturn(Optional.of(mockedEmployee));
        when(departmenteRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(employeeRepository.save(any())).thenReturn(mockedEmployee);
        when(employeeMapper.mapEntityToDTO(any())).thenReturn(mockedEmployeeDTO);
        
        ResponseWrapper<EmployeeDTO> response = employeeService.putEmployee(employee, any());
        assertNotNull(response.getData());
        assertNull(response.getError());
        assertEquals(mockedEmployeeDTO, response.getData());  
	}
}
