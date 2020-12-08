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

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.takeaway.challenge.dto.DepartmentDTO;
import com.takeaway.challenge.mappers.DepartmentMapper;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.service.DepartmentServiceImpl;
import com.takeaway.challenge.util.ResponseWrapper;

/**
 * @author Naveen Kumashi
 */

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTests {
	@Mock
    private EntityManager entityManager;
	
	@Mock
    private DepartmentMapper departmentMapper;
	
	@Mock 
	private DepartmentRepository departmenteRepository;		
	
	@Mock
	private Employee mockedEmployee;
	
	@Mock
	private DepartmentDTO mockedDepartmentDTO;
	
	@Mock
    private Department mockedDepartment;
	
	@InjectMocks
    private DepartmentServiceImpl departmentService;
	
	@Test
	public void should_create_a_department() {				        
        departmentService.postDepartment(mockedDepartment);
        verify(departmenteRepository).save(any());
        verify(departmentMapper, times(1)).mapEntityToDTO(any());	
	}
	
	@Test
	public void should_fetch_all_departments() {
		List<Department> departmentList = new ArrayList<Department>();
		departmentList.add(mockedDepartment);
		when(departmenteRepository.findAll()).thenReturn(departmentList);
		
		departmentService.getAllDepartments();
		verify(departmenteRepository).findAll();
		verify(departmentMapper, times(1)).mapEntityListToDTOList(anyList());	
	}
	
	@Test
	public void should_fetch_a_department_by_id() {		
		when(departmenteRepository.findById(any())).thenReturn(Optional.of(mockedDepartment));
		when(departmentMapper.mapEntityToDTO(any())).thenReturn(mockedDepartmentDTO);
		
		ResponseWrapper<DepartmentDTO> response = departmentService.getById(mockedDepartment.getId());
        assertNotNull(response.getData());
        assertNull(response.getError());
        assertEquals(mockedDepartmentDTO, response.getData());
	}
	
	@Test
	public void should_update_a_department_by_id() {
        when(departmenteRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        
        departmentService.putEmployee(mockedDepartment, mockedDepartment.getId());
        verify(departmenteRepository).save(any());
        verify(departmentMapper, times(1)).mapEntityToDTO(any());	 
	}
}
