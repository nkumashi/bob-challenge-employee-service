package com.takeaway.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.takeaway.challenge.hateos.assembler.DepartmentModelAssembler;
import com.takeaway.challenge.hateos.model.DepartmentModel;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.service.DepartmentServiceImpl;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTests {		
	@Mock
	private DepartmentModelAssembler departmentModelAssembler;
	
	@Mock 
	private DepartmentRepository departmenteRepository;		
	
	@Mock
	private DepartmentModel mockedDepartmentModel;
	
	@Mock
    private Department mockedDepartment;
	
	@InjectMocks
    private DepartmentServiceImpl departmentService;
	
	@Test
	public void should_create_a_department() {				        
        departmentService.postDepartment(mockedDepartment);
        verify(departmenteRepository).save(any());
        verify(departmentModelAssembler, times(1)).toModel(any());	
	}
	
	@Test
	public void should_fetch_all_departments() {
		List<Department> departmentList = new ArrayList<Department>();
		departmentList.add(mockedDepartment);
		when(departmenteRepository.findAll()).thenReturn(departmentList);
		
		departmentService.getAllDepartments();
		verify(departmenteRepository).findAll();
		verify(departmentModelAssembler, times(1)).toCollectionModel(anyList());	
	}
	
	@Test
	public void should_fetch_a_department_by_id() {		
		when(departmenteRepository.findById(any())).thenReturn(Optional.of(mockedDepartment));
		when(departmentModelAssembler.toModel(any())).thenReturn(mockedDepartmentModel);
		
		ResponseWrapper<DepartmentModel> response = departmentService.getDepartmentById(mockedDepartment.getId());
        assertNotNull(response.getData());
        assertNull(response.getError());
        assertEquals(mockedDepartmentModel, response.getData());
	}
	
	@Test
	public void should_update_a_department_by_id() {
        when(departmenteRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        
        departmentService.putEmployee(mockedDepartment, mockedDepartment.getId());
        verify(departmenteRepository).save(any());
        verify(departmentModelAssembler, times(1)).toModel(any());	 
	}
}
