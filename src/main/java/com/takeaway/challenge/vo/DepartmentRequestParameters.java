package com.takeaway.challenge.vo;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.takeaway.challenge.hateos.model.DepartmentModel;
import com.takeaway.challenge.model.Department;

/**
 * @author Naveen Kumashi
 */

public class DepartmentRequestParameters {	
	@NotNull(message = "Department name cannot be null/empty.")
	private String departmentName;
	
	@JsonCreator
    public DepartmentRequestParameters(    		            
            @JsonProperty("departmentName") String departmentName) {        
        this.departmentName = departmentName;              
    }
	
	public DepartmentModel toDTO() {
		ModelMapper modelMapper = new ModelMapper();			    
		return modelMapper.map(this, DepartmentModel.class);
	}
	
	public Department toEntity() {
		Department department = new Department();		
		department.setName(this.departmentName);
		
		return department;
	}
}
