package com.hr.recruitmenthandler.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.hr.recruitmenthandler.RecruitmenthandlerApplication;
import com.hr.recruitmenthandler.model.JobApplication;
import com.hr.recruitmenthandler.service.application.JobApplicationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RecruitmenthandlerApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class JobApplicationControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private JobApplicationService jobApplicationService;

	private final String URL = "/applications/";

	@Test
	public void givenJobApplications_whenGetJobApplication_thenReturnJson() throws Exception {
		//given
		JobApplication application = new JobApplication(1L, 1L, "test@app.com", "test", "APPLIED");

		when(jobApplicationService.findApplicationByApplicationId(any(Long.class))).thenReturn(application);

		// when
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "{id}", new Long(1)).accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();

		// then
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(jobApplicationService).findApplicationByApplicationId(any(Long.class));

		JobApplication retrievedJobApplication = FormatConversionUtil.jsonToObject(result.getResponse().getContentAsString(), JobApplication.class);
		assertNotNull(retrievedJobApplication);
		assertEquals(1l, retrievedJobApplication.getApplicationId().longValue());		
	}
	
	@Test
	public void givenNewApplication_whenCreateApplication_thenReturn201OK() throws Exception {

		// given
		JobApplication application = new JobApplication(2L, 1L, "create@app.com", "test", "APPLIED");
		when(jobApplicationService.saveApplication(any(JobApplication.class))).thenReturn(application);

		// when
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(FormatConversionUtil.objectToJson(application))).andReturn();

		// then
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);


	}

	


}
