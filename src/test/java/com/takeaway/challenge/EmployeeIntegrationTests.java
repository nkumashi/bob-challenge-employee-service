package com.takeaway.challenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.challenge.vo.EmployeeRequestParameters;

/**
 * @author Naveen Kumashi
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTests {
	@Autowired
	private MockMvc mockMvc;
	
	private JacksonTester<EmployeeRequestParameters> employeeJSON;
	
	@BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }
	
	@Test
	void should_fail_creating_an_employee_no_auth_provided() throws Exception {		
		EmployeeRequestParameters params = new EmployeeRequestParameters();
		params.setDepartmentId(1l);
		params.setDepartmentName("Dummy");
		params.setEmail("dummy@email.com");
		params.setFirstName("First name");
		params.setLastName("Last name");
		params.setDob(LocalDate.now());
	 		
		mockMvc.perform(post("/api/employee")
	            .contentType("application/json")
	            .content(employeeJSON.write(params).getJson()))
	            .andExpect(status().is4xxClientError());     
	}
	
	@Test
	void should_fetch_employees() throws Exception {					 	
		mockMvc.perform(get("/api/employees")
	            .contentType("application/json"))
	            .andExpect(status().isOk());   
	}
}
