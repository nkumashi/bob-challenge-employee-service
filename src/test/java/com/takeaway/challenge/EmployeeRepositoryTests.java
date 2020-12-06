package com.takeaway.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
	private TestEntityManager entityManager;
	@Autowired 
	private EmployeeRepository employeeRepository;
	@Autowired 
	private DepartmentRepository departmenteRepository;
	
	@Test
	void contextLoads() {
		assertThat(entityManager).isNotNull();
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
		departmenteRepository.save(department);
		assertThat(department).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, department));
		
		Iterable<Employee> employees = employeeRepository.findAll();
		assertThat(employees).isNotEmpty();
	}

	@Test
	public void should_create_an_employee() {
		Department department = new Department("Dummy");
		departmenteRepository.save(department);
		assertThat(department).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee employee = employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, department));
		assertThat(employee).hasFieldOrPropertyWithValue("email", "dummy@email.com");
		assertThat(employee).hasFieldOrPropertyWithValue("firstName", "First name");
		assertThat(employee).hasFieldOrPropertyWithValue("lastName", "Last name");
		assertThat(employee).hasFieldOrPropertyWithValue("dob", dob);
	}
	
	@Test
	public void should_update_an_employee() {
		Department department = new Department("Dummy");
		departmenteRepository.save(department);
		assertThat(department).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee employee = employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, department));
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
		departmenteRepository.save(department);
		assertThat(department).hasFieldOrPropertyWithValue("name", "Dummy");
		
		LocalDate dob = LocalDate.now();
		Employee employee = employeeRepository.save(new Employee("dummy@email.com", "First name", "Last name", dob, department));
		assertThat(employee).hasFieldOrPropertyWithValue("email", "dummy@email.com");
		assertThat(employee).hasFieldOrPropertyWithValue("firstName", "First name");
		assertThat(employee).hasFieldOrPropertyWithValue("lastName", "Last name");
		assertThat(employee).hasFieldOrPropertyWithValue("dob", dob);
		
		employeeRepository.delete(employee);
		Optional<Employee> optional = employeeRepository.findById(employee.getId());
		System.out.println("deleted: " + optional.isPresent());
		assertTrue(optional.isPresent() == false);
	}
}
