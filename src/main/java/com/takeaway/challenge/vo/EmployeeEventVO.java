package com.takeaway.challenge.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen Kumashi
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEventVO {
	private String eventName;
	private String eventTimestamp;
}
