package com.takeaway.challenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.challenge.vo.DepartmentRequestParameters;

/**
 * @author Naveen Kumashi
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentIntegrationTests {
	@Autowired
	private MockMvc mockMvc;
	
	private JacksonTester<DepartmentRequestParameters> json;
	
	@BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }
	
	@Test
	void should_fail_creating_a_department_no_auth_provided() throws Exception {		
		DepartmentRequestParameters params = new DepartmentRequestParameters();
		params.setDepartmentId(1l);
		params.setDepartmentName("Dummy");
	 		
		mockMvc.perform(post("/api/department")
	            .contentType("application/json")
	            .content(json.write(params).getJson()))
	            .andExpect(status().is4xxClientError());     
	}
	
	@Test
	void should_succeed_creating_a_department_auth_provided() throws Exception {		
		DepartmentRequestParameters params = new DepartmentRequestParameters();
		params.setDepartmentId(1l);
		params.setDepartmentName("Dummy");
	 		
		mockMvc.perform(post("/api/department")
				.header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("admin:admin123".getBytes()))
	            .contentType("application/json")
	            .content(json.write(params).getJson()))
	            .andExpect(status().is2xxSuccessful());     
	}
	
	@Test
	void should_fetch_departments() throws Exception {			 	
		mockMvc.perform(get("/api/departments")
	            .contentType("application/json"))
	            .andExpect(status().isOk());   
	}
}
