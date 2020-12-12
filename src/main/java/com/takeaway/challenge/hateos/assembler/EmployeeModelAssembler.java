package com.takeaway.challenge.hateos.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import com.takeaway.challenge.controller.EmployeeController;
import com.takeaway.challenge.hateos.model.EmployeeModel;
import com.takeaway.challenge.model.Employee;

/**
 * @author Naveen Kumashi
 */

@Component
public class EmployeeModelAssembler extends RepresentationModelAssemblerSupport<Employee, EmployeeModel> {
	private DepartmentModelAssembler departmentModelAssembler;
	
	public EmployeeModelAssembler(DepartmentModelAssembler departmentModelAssembler) {
        super(EmployeeController.class, EmployeeModel.class);
        this.departmentModelAssembler = departmentModelAssembler;
    }

	@Override
	public EmployeeModel toModel(Employee entity) {
		EmployeeModel employeeModel = instantiateModel(entity);
		employeeModel.add(linkTo(
                methodOn(EmployeeController.class)
                .getEmployeeById(entity.getId().toString()))
                .withSelfRel());
							       
		employeeModel.setEmployeeId(entity.getId());
		employeeModel.setEmail(entity.getEmail());
		employeeModel.setFirstName(entity.getFirstName());
		employeeModel.setLastName(entity.getLastName());
		employeeModel.setDepartment(departmentModelAssembler.toModel(entity.getDepartment()));

        return employeeModel;
	}
	
	@Override
    public CollectionModel<EmployeeModel> toCollectionModel(Iterable<? extends Employee> entities) {
        CollectionModel<EmployeeModel> employeeModels = super.toCollectionModel(entities);         
        employeeModels.add(linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());         
        return employeeModels;
    }
}
