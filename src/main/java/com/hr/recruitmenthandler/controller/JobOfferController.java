package com.hr.recruitmenthandler.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hr.recruitmenthandler.exceptionhandler.EntityNotFoundException;
import com.hr.recruitmenthandler.model.JobApplication;
import com.hr.recruitmenthandler.model.JobOffer;
import com.hr.recruitmenthandler.service.application.JobApplicationService;
import com.hr.recruitmenthandler.service.offer.JobOfferService;
import com.hr.recruitmenthandler.util.JobApplicationError;


@RestController
@RequestMapping(value = "/offers", produces = { MediaType.APPLICATION_JSON_VALUE })
public class JobOfferController {
	
	private static final Logger logger = LoggerFactory.getLogger(JobOfferController.class);

	@Autowired
	JobOfferService jobOfferService;
	
	@Autowired
	JobApplicationService jobApplicationService;
	
	/**
	 * gets a single offer with given primary key
	 * @param offerId
	 * @return
	 * @throws EntityNotFoundException
	 */
	@RequestMapping(value = "/{offerId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getOffer(@PathVariable(value="offerId", required=true) Long offerId)  throws EntityNotFoundException{
		JobOffer offer = jobOfferService.findOfferByOfferId(offerId);
		if (offer == null) {
			throw new EntityNotFoundException(JobOffer.class, "offerId", offerId.toString());
		}
		return new ResponseEntity<JobOffer>(offer, HttpStatus.OK);
	}
	
	/**
	 * Loads all the applications against a single offer and returns Json array
	 * @param offerId
	 * @return
	 * @throws EntityNotFoundException
	 */
	@RequestMapping(value = "/{offerId}/applications", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getAllOffersForApplication(@PathVariable(value="offerId", required=true) Long offerId) throws EntityNotFoundException {
		List<JobApplication> OffersList = jobApplicationService.findApplicationByOfferId(offerId);
		return new ResponseEntity<List<JobApplication>>(OffersList, HttpStatus.OK);
	}
	
	
	/**
	 * lists all offers and returns Json array
	 * @return
	 * @throws EntityNotFoundException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<JobOffer>> listAllOffers()  throws EntityNotFoundException{
        List<JobOffer> users = jobOfferService.findAllOffers();
        
        return new ResponseEntity<List<JobOffer>>(users, HttpStatus.OK);
    }
	
	/**
	 * create new offer 
	 * @param offer
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createOffer(@RequestBody JobOffer offer, UriComponentsBuilder ucBuilder) {
		String jobTitle = offer.getJobTitle();
       if (jobOfferService.isOfferExists(jobTitle)) {
            logger.error("Unable to create Offer. An Offer with email {} already exist", offer.getJobTitle());
            return new ResponseEntity<JobApplicationError>(new JobApplicationError("An offer with this title already exists."),HttpStatus.CONFLICT);
        }
		
       	offer = jobOfferService.createNewOffer(offer);
 
       	//add link for self
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(offer.getOfferId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }


	/**
	 * @param jobOfferService
	 */
	public void setJobOfferService(JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
	
	

}
