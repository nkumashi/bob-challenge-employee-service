package com.takeaway.challenge.vo;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.takeaway.challenge.hateos.model.DepartmentModel;
import com.takeaway.challenge.model.Department;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen Kumashi
 */

@Data
@NoArgsConstructor
public class DepartmentRequestParameters {	
	private Long departmentId;
	
	@NotNull(message = "Department name cannot be null/empty.")
	private String departmentName;
	
	@JsonCreator
    public DepartmentRequestParameters(    		            
    		@JsonProperty("departmentId") Long departmentId,
            @JsonProperty("departmentName") String departmentName) {    
		this.departmentId = departmentId;
        this.departmentName = departmentName;              
    }
	
	public DepartmentModel toDTO() {
		ModelMapper modelMapper = new ModelMapper();			    
		return modelMapper.map(this, DepartmentModel.class);
	}
	
	public Department toEntity() {
		Department department = new Department();	
		if(!ObjectUtils.isEmpty(this.departmentId)) {
			department.setId(this.departmentId);
		}			
		department.setName(this.departmentName);
		
		return department;
	}
}
