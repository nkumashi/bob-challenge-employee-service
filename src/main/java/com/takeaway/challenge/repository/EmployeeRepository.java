package com.takeaway.challenge.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.takeaway.challenge.model.Employee;

/**
 * @author Naveen Kumashi
 */

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
	Optional<Employee> findById(@Param("uuid") UUID uuid);
	List<Employee> findByEmail(@Param("email") String email);
}
