package com.takeaway.challenge.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.challenge.dto.DepartmentDTO;
import com.takeaway.challenge.service.DepartmentService;
import com.takeaway.challenge.util.ResponseWrapper;
import com.takeaway.challenge.vo.DepartmentRequestParameters;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Naveen Kumashi
 */

@RestController
@RequestMapping(value = "/api")
@Api(value = "Department")
public class DepartmentController {
	private DepartmentService departmentService;
	
	public DepartmentController(
			DepartmentService departmentService
	) {
		this.departmentService = departmentService;	
	}
	
	@ApiOperation(value = "View department information by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = DepartmentDTO.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@PostMapping("/department")
    public ResponseEntity<Object> postDepartment(@Valid @RequestBody DepartmentRequestParameters parameters) {
		ResponseWrapper<DepartmentDTO> response = departmentService.postDepartment(parameters.toEntity());

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
}
