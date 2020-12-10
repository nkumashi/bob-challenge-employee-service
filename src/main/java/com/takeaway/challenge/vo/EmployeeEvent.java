package com.takeaway.challenge.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen Kumashi
 */

@Data
@NoArgsConstructor
public class EmployeeEvent {
	private String eventName;
	private LocalDateTime eventTimestamp;
}
