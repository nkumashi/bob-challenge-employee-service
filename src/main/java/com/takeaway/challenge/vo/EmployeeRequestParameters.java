package com.takeaway.challenge.vo;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
//import com.takeaway.challenge.dto.EmployeeDTO;
import com.takeaway.challenge.hateos.model.EmployeeModel;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen Kumashi
 */

@Data
@NoArgsConstructor
public class EmployeeRequestParameters {	
	private String employeeId;
	
	@NotNull(message = "Department Id cannot be null/empty.")
	private Long departmentId;
	
	@NotEmpty(message = "Email cannot be null/empty.")
	private String email;
	
	@NotEmpty(message = "First name cannot be null/empty.")
	private String firstName;
	
	@NotEmpty(message = "Last name cannot be null/empty.")
	private String lastName;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Date of birth cannot be null/empty.")
	private LocalDate dob;
	
	@NotEmpty(message = "Department name cannot be null/empty.")
	private String departmentName;
	
	@JsonCreator
    public EmployeeRequestParameters(    
    		@JsonProperty("employeeId") String employeeId,
            @JsonProperty("departmentId") Long departmentId,
            @JsonProperty("departmentName") String departmentName,
            @JsonProperty("email") String email,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("dob") LocalDate dob) {
		this.employeeId = employeeId;
        this.departmentId = departmentId;
        this.departmentName = departmentName;   
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;            
    }
	
	public EmployeeModel toDTO() {
		ModelMapper modelMapper = new ModelMapper();			    
		return modelMapper.map(this, EmployeeModel.class);
	}
	
	public Employee toEntity() {
		Department department = new Department();
		department.setId(this.departmentId);
		department.setName(this.departmentName);
		
		Employee employee = new Employee();		
		if(!ObjectUtils.isEmpty(this.employeeId)) {
			employee.setId(UUID.fromString(employeeId));
		}		
		employee.setDepartment(department);
		employee.setEmail(this.email);
		employee.setFirstName(this.firstName);
		employee.setLastName(this.lastName);
		employee.setDob(this.dob);
		return employee;
	}
}
