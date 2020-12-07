package com.takeaway.challenge.vo;

import java.util.StringJoiner;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen Kumashi
 */

@Data
@NoArgsConstructor
public class EmployeeId {
	@NotEmpty(message = "UUID cannot be null/empty.")
    private UUID employeeId;
    
    public EmployeeId(UUID employeeId) {
    	this.employeeId = employeeId;
    }
    
    @JsonValue
    public UUID getEmployeeId() {
        return employeeId;
    }

    @JsonCreator
    public static EmployeeId fromString(String id) {
        return new EmployeeId(UUID.fromString(id));
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", EmployeeId.class.getSimpleName() + "[", "]")
                .add(String.format("uuid = %s", employeeId))
                .toString();
    }
}