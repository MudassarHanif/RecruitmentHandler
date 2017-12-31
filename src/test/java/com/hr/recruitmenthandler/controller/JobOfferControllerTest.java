package com.hr.recruitmenthandler.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.hr.recruitmenthandler.model.JobOffer;
import com.hr.recruitmenthandler.service.offer.JobOfferService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RecruitmenthandlerApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class JobOfferControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private JobOfferService jobOfferService;

	private final String URL = "/offers/";

	@Test
	public void givenJobOffers_whenGetJobOffers_thenReturnJson() throws Exception {
		//given
		JobOffer offer = new JobOffer(1L, null, "Junior Developer", 0);

		when(jobOfferService.findOfferByOfferId(any(Long.class))).thenReturn(offer);

		// when
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "{id}", new Long(1)).accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();

		// then
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(jobOfferService).findOfferByOfferId(any(Long.class));

		JobOffer retrievedJobOffer = FormatConversionUtil.jsonToObject(result.getResponse().getContentAsString(), JobOffer.class);
		assertNotNull(retrievedJobOffer);
		assertEquals(1l, retrievedJobOffer.getOfferId().longValue());		
	}
	
	@Test
	public void givenNoOffers_whenFindJobOffer_thenReturn404NotExist() throws Exception {

		// Given - Not Required as no offer Exists in this scenario

		// when
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "{id}", new Long(28)).accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();

		// then
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.NOT_FOUND.value(), status);

		// verify that service method was called once
		verify(jobOfferService).findOfferByOfferId(any(Long.class));

		JobOffer jobOffer = FormatConversionUtil.jsonToObject(result.getResponse().getContentAsString(), JobOffer.class);
		assertNull(jobOffer.getOfferId());
	}
	
	


}
