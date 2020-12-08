package com.takeaway.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;

/**
 * @author Naveen Kumashi
 */

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
class EmployeeRepositoryTests {
	@Autowired 
	private EmployeeRepository employeeRepository;
	@Autowired 
	private DepartmentRepository departmenteRepository;
	
	@Test
	void contextLoads() {
		assertThat(employeeRepository).isNotNull();
	}
	
	@Test
	public void should_find_no_employees_if_repository_is_empty() {
		Iterable<Employee> employees = employeeRepository.findAll();

		assertThat(employees).isEmpty();
	}
	
	@Test
	public void should_find_employees() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, addedDepartment));
		
		Iterable<Employee> employees = employeeRepository.findAll();
		assertThat(employees).isNotEmpty();
	}

	@Test
	public void should_find_employee_by_id() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee addedEmployee = employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, addedDepartment));
		
		Optional<Employee> employee = employeeRepository.findById(addedEmployee.getId());
		assertNotNull(employee);
	}
	
	@Test
	public void should_find_employee_by_email() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee addedEmployee = employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, addedDepartment));
		
		List<Employee> employees = employeeRepository.findByEmail(addedEmployee.getEmail());
		assertThat(employees).isNotEmpty();
		assertTrue(employees.size() == 1);
	}
	
	@Test
	public void should_create_an_employee() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee employee = employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, addedDepartment));
		assertThat(employee).hasFieldOrPropertyWithValue("email", "dummy@email.com");
		assertThat(employee).hasFieldOrPropertyWithValue("firstName", "First name");
		assertThat(employee).hasFieldOrPropertyWithValue("lastName", "Last name");
		assertThat(employee).hasFieldOrPropertyWithValue("dob", dob);
	}
	
	@Test
	public void should_update_an_employee() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee employee = employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, addedDepartment));
		assertThat(employee).hasFieldOrPropertyWithValue("email", "dummy@email.com");
		assertThat(employee).hasFieldOrPropertyWithValue("firstName", "First name");
		assertThat(employee).hasFieldOrPropertyWithValue("lastName", "Last name");
		assertThat(employee).hasFieldOrPropertyWithValue("dob", dob);
		
		employee.setEmail("new-dummy@email.com");
		employee.setLastName("Latest name");
		Employee updatedEmployee = employeeRepository.save(employee);
		assertThat(updatedEmployee).hasFieldOrPropertyWithValue("email", "new-dummy@email.com");
		assertThat(updatedEmployee).hasFieldOrPropertyWithValue("lastName", "Latest name");
	}
	
	@Test
	public void should_delete_an_employee() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee employee = employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, addedDepartment));
		assertThat(employee).hasFieldOrPropertyWithValue("email", "dummy@email.com");
		assertThat(employee).hasFieldOrPropertyWithValue("firstName", "First name");
		assertThat(employee).hasFieldOrPropertyWithValue("lastName", "Last name");
		assertThat(employee).hasFieldOrPropertyWithValue("dob", dob);
		
		employeeRepository.delete(employee);
		Optional<Employee> optional = employeeRepository.findById(employee.getId());
		assertTrue(optional.isPresent() == false);
	}
}
