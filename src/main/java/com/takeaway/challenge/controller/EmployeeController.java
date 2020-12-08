package com.takeaway.challenge.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.challenge.hateos.model.EmployeeModel;
import com.takeaway.challenge.service.EmployeeService;
import com.takeaway.challenge.util.ResponseWrapper;
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

	@ApiOperation(value = "Save a new Employee record")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = EmployeeModel.class),
			@ApiResponse(code = 400, message = "Bad request")
	})
	@PostMapping(value = "/employee", produces = { "application/hal+json" })
    public ResponseEntity<Object> postEmployee(@Valid @RequestBody EmployeeRequestParameters parameters) {
		ResponseWrapper<EmployeeModel> response = employeeService.postEmployee(parameters.toEntity());

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
	
	@ApiOperation(value = "Fetch all employees")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = List.class)
	})
	@GetMapping(value = "/employees", produces = { "application/hal+json" })
    public ResponseEntity<Object> getAllEmployees() {
		ResponseWrapper<CollectionModel<EmployeeModel>> response = employeeService.getAllEmployees();
        return new ResponseEntity<>(response.getData(),null, HttpStatus.OK);        
    }
	
	@ApiOperation(value = "View employee information by UUID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = EmployeeModel.class),
			@ApiResponse(code = 400, message = "Bad request")
	})
	@GetMapping(value = "/employee/{employeeId}", produces = { "application/hal+json" })
    public ResponseEntity<Object> getEmployeeById(@Valid @PathVariable("employeeId") String employeeId) {
		ResponseWrapper<EmployeeModel> response = employeeService.getEmployeeById(UUID.fromString(employeeId));

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
	
	@ApiOperation(value = "Update employee information")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = EmployeeModel.class),
			@ApiResponse(code = 400, message = "Bad request")
	})
	@PutMapping(value = "/employee/{employeeId}", produces = { "application/hal+json" })
    public ResponseEntity<Object> putEmployee(@Valid @RequestBody EmployeeRequestParameters params) {
		ResponseWrapper<EmployeeModel> response = employeeService.putEmployee(params.toEntity());

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
	
	@ApiOperation(value = "Delete employee record")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = EmployeeModel.class),
			@ApiResponse(code = 400, message = "Bad request")
	})
	@DeleteMapping(value = "/employee/{employeeId}", produces = { "application/hal+json" })
    public ResponseEntity<Object> deleteEmployee(@PathVariable String employeeId) {
		ResponseWrapper<Object> response = employeeService.deleteEmployeeById(UUID.fromString(employeeId));

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
}
