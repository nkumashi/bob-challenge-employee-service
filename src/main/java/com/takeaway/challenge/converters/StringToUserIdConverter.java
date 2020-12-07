package com.takeaway.challenge.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.takeaway.challenge.vo.EmployeeId;

import java.util.UUID;

/**
 * @author Naveen Kumashi
 */

@Component 
public class StringToUserIdConverter implements Converter<String, EmployeeId> { 
	@Override
	public EmployeeId convert(String uuid) {
		return new EmployeeId(UUID.fromString(uuid)); 
	}
}
