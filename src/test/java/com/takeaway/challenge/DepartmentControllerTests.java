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
import com.takeaway.challenge.controller.DepartmentController;
import com.takeaway.challenge.service.DepartmentService;
import com.takeaway.challenge.vo.DepartmentRequestParameters;

/**
 * @author Naveen Kumashi
 */

@WebMvcTest(controllers = DepartmentController.class)
public class DepartmentControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DepartmentService departmentService;
	
	@InjectMocks
    private DepartmentController departmentController;
	
	private JacksonTester<DepartmentRequestParameters> json;
	
	@BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .build();
    }
	
	@Test
	void should_succeed_creating_a_department() throws Exception {		
		DepartmentRequestParameters params = new DepartmentRequestParameters();
		params.setDepartmentId(1l);
		params.setDepartmentName("Dummy");
	  
		MockHttpServletResponse response = mockMvc.perform(
                post("/api/department").contentType(MediaType.APPLICATION_JSON).content(
                        json.write(params).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());       
	}
}
