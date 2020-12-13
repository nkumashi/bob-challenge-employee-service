package com.takeaway.challenge.controller;

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

import com.takeaway.challenge.hateos.model.DepartmentModel;
import com.takeaway.challenge.service.DepartmentService;
import com.takeaway.challenge.util.ResponseWrapper;
import com.takeaway.challenge.vo.DepartmentRequestParameters;

/**
 * @author Naveen Kumashi
 */

@RestController
@RequestMapping(value = "/api")
public class DepartmentController {
	private DepartmentService departmentService;
	
	public DepartmentController(
			DepartmentService departmentService
	) {
		this.departmentService = departmentService;	
	}
	
	@PostMapping(value = "/department", produces = { "application/hal+json" })
    public ResponseEntity<Object> postDepartment(@Valid @RequestBody DepartmentRequestParameters parameters) {
		ResponseWrapper<DepartmentModel> response = departmentService.postDepartment(parameters.toEntity());

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

	@GetMapping(value = "/departments", produces = { "application/hal+json" })
    public ResponseEntity<Object> getAllDepartments() {
		ResponseWrapper<CollectionModel<DepartmentModel>> response = departmentService.getAllDepartments();
        return new ResponseEntity<>(response.getData(),null, HttpStatus.OK);        
    }
	
	@GetMapping(value = "/department/{departmentId}", produces = { "application/hal+json" })
    public ResponseEntity<Object> getDepartmentById(@Valid @PathVariable("departmentId") Long departmentId) {
		ResponseWrapper<DepartmentModel> response = departmentService.getDepartmentById(departmentId);

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
	
	@PutMapping(value = "/department", produces = { "application/hal+json" })
    public ResponseEntity<Object> putDepartment(@Valid @RequestBody DepartmentRequestParameters params) {
		ResponseWrapper<DepartmentModel> response = departmentService.putDepartment(params.toEntity());

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
	
	@DeleteMapping(value = "/department/{departmentId}", produces = { "application/hal+json" })
    public ResponseEntity<Object> deleteDepartment(@Valid @PathVariable("departmentId") Long departmentId) {
		ResponseWrapper<Object> response = departmentService.deleteDepartmentById(departmentId);

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
}
