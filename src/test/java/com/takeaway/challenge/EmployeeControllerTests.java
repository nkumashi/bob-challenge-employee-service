package com.takeaway.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.challenge.controller.EmployeeController;
import com.takeaway.challenge.service.EmployeeService;
import com.takeaway.challenge.vo.EmployeeRequestParameters;

/**
 * @author Naveen Kumashi
 */

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	@InjectMocks
    private EmployeeController employeeController;
	
	private JacksonTester<EmployeeRequestParameters> json;
	
	@BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .build();
    }
	
	@Test
	void should_succeed_creating_an_employee() throws Exception {		
		LocalDate dob = LocalDate.now();
		EmployeeRequestParameters params = new EmployeeRequestParameters();
		params.setDepartmentId(1l);
		params.setDepartmentName("Dummy");
		params.setEmail("dummy@email.com");
		params.setFirstName("");
		params.setLastName("Last name");
		params.setDob(dob);
	  
		MockHttpServletResponse response = mockMvc.perform(
                post("/api/employee").contentType(MediaType.APPLICATION_JSON).content(
                        json.write(params).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());       
	}
	
	@Test
	void should_fail_creating_an_employee_departmentId_does_not_exist() throws Exception {		
		LocalDate dob = LocalDate.now();
		EmployeeRequestParameters params = new EmployeeRequestParameters();
		params.setDepartmentId(1l);
		params.setDepartmentName("Dummy");
		params.setEmail("dummy@email.com");
		params.setFirstName("First name");
		params.setLastName("Last name");
		params.setDob(dob);
	  
		MockHttpServletResponse response = mockMvc.perform(
                post("/api/employee").contentType(MediaType.APPLICATION_JSON).content(
                        json.write(params).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());       
	}
	
	@Test
	void should_fail_creating_an_employee_invalid_dob() throws Exception {		
		EmployeeRequestParameters params = new EmployeeRequestParameters();
		params.setDepartmentId(1l);
		params.setDepartmentName("Dummy");
		params.setEmail("dummy@email.com");
		params.setFirstName("First name");
		params.setLastName("Last name");
	  
		MockHttpServletResponse response = mockMvc.perform(
                post("/api/employee").contentType(MediaType.APPLICATION_JSON).content(
                        json.write(params).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());       
	}
	
	@Test
	void should_fail_creating_an_employee_invalid_firstname() throws Exception {		
		LocalDate dob = LocalDate.now();
		EmployeeRequestParameters params = new EmployeeRequestParameters();
		params.setDepartmentId(1l);
		params.setDepartmentName("Dummy");
		params.setEmail("dummy@email.com");
		params.setFirstName("");
		params.setLastName("Last name");
		params.setDob(dob);
	  
		MockHttpServletResponse response = mockMvc.perform(
                post("/api/employee").contentType(MediaType.APPLICATION_JSON).content(
                        json.write(params).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());       
	}		
}
