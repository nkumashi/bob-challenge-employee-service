package com.takeaway.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.repository.DepartmentRepository;

/**
 * @author Naveen Kumashi
 */

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
public class DepartmentRepositoryTests {
	@Autowired 
	private DepartmentRepository departmenteRepository;
	
	@Test
	void contextLoads() {
		assertThat(departmenteRepository).isNotNull();		
	}
	
	@Test
	public void should_find_no_departments_if_repository_is_empty() {
		departmenteRepository.deleteAll();
		Iterable<Department> departments = departmenteRepository.findAll();

		assertThat(departments).isEmpty();
	}
	
	@Test
	public void should_find_department() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");				
		
		Iterable<Department> departments = departmenteRepository.findAll();
		assertThat(departments).isNotEmpty();
	}
	
	@Test
	public void should_find_department_by_id() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");				
		
		Optional<Department> optional = departmenteRepository.findById(addedDepartment.getId());
		assertTrue(optional.isPresent());
	}
	
	@Test
	public void should_create_a_department() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");
		assertNotNull(addedDepartment.getId());		
	}
	
	@Test
	public void should_update_a_department() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");	
		
		addedDepartment.setName("Latest name");
		Department updatedDepartment = departmenteRepository.save(addedDepartment);
		assertThat(updatedDepartment).hasFieldOrPropertyWithValue("name", "Latest name");
	}
	
	@Test
	public void should_delete_a_department() {
		Department department = new Department("Dummy");
		Department addedDepartment = departmenteRepository.save(department);
		assertThat(addedDepartment).hasFieldOrPropertyWithValue("name", "Dummy");				
		
		departmenteRepository.delete(addedDepartment);
		Optional<Department> optional = departmenteRepository.findById(department.getId());
		assertTrue(optional.isPresent() == false);
	}
}
