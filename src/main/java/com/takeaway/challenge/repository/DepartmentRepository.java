package com.takeaway.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeaway.challenge.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
