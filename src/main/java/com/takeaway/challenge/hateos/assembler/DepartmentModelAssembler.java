package com.takeaway.challenge.hateos.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.takeaway.challenge.controller.DepartmentController;
import com.takeaway.challenge.hateos.model.DepartmentModel;
import com.takeaway.challenge.model.Department;

/**
 * @author Naveen Kumashi
 */

@Component
public class DepartmentModelAssembler extends RepresentationModelAssemblerSupport<Department, DepartmentModel> {
	public DepartmentModelAssembler() {
        super(DepartmentModelAssembler.class, DepartmentModel.class);
    }

	@Override
	public DepartmentModel toModel(Department entity) {
		DepartmentModel departmentModel = instantiateModel(entity);
		departmentModel.add(linkTo(
                methodOn(DepartmentController.class)
                .getDepartmentById(entity.getId()))
                .withSelfRel());
							       
		departmentModel.setDepartmentId(entity.getId());
		departmentModel.setDepartmentName(entity.getName());		

        return departmentModel;
	}
	
	@Override
    public CollectionModel<DepartmentModel> toCollectionModel(Iterable<? extends Department> entities) {
        CollectionModel<DepartmentModel> departmentModels = super.toCollectionModel(entities);         
        departmentModels.add(linkTo(methodOn(DepartmentController.class).getAllDepartments()).withSelfRel());         
        return departmentModels;
    }
}
