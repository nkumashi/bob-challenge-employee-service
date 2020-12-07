package com.takeaway.challenge.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.challenge.dto.EmployeeDTO;
import com.takeaway.challenge.service.EmployeeService;
import com.takeaway.challenge.util.ResponseWrapper;
import com.takeaway.challenge.vo.EmployeeId;
import com.takeaway.challenge.vo.EmployeeRequestParameters;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Naveen Kumashi
 */

@RestController
@RequestMapping(value = "/api")
@Api(value = "Employees")
public class EmployeeController {
	private EmployeeService employeeService;

	public EmployeeController(
			EmployeeService employeeService
			) {
		this.employeeService = employeeService;
	}

	@GetMapping("/home")
	public String home() {		
		return "Hello from Server!";
	}

	@ApiOperation(value = "View employee information by UUID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = EmployeeDTO.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@PostMapping("/employee")
    public ResponseEntity<Object> postEmployee(@Valid @RequestBody EmployeeRequestParameters parameters) {
		ResponseWrapper<EmployeeDTO> response = employeeService.postEmployee(parameters.toEntity());

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
}
