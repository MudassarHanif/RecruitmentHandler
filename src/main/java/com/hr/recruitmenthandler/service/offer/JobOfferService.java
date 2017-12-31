package com.hr.recruitmenthandler.service.offer;

import java.util.List;

import com.hr.recruitmenthandler.exceptionhandler.EntityNotFoundException;
import com.hr.recruitmenthandler.model.JobOffer;

public interface JobOfferService {

	List<JobOffer> findAllOffers() throws EntityNotFoundException;
	JobOffer findOfferByOfferId(Long offerId) throws EntityNotFoundException;
	JobOffer createNewOffer(JobOffer offer);
	boolean isOfferExists(String jobTitle);
	
	
}
