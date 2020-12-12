package com.takeaway.challenge.hateos.model;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Naveen Kumashi
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "employee")
@Relation(collectionRelation = "employees")
@JsonInclude(Include.NON_NULL)
public class EmployeeModel extends RepresentationModel<EmployeeModel> {	
	private UUID employeeId;	
	private String email;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private DepartmentModel department;	
}
