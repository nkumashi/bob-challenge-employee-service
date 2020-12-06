package com.takeaway.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.takeaway.challenge.model.Department;

/**
 * @author Naveen Kumashi
 */

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	Optional<Department> findById(@Param("id") Long id);
}
