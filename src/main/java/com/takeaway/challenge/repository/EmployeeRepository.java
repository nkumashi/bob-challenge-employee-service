package com.takeaway.challenge.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeaway.challenge.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

}
