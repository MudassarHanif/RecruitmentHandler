package com.hr.recruitmenthandler.service.offer;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hr.recruitmenthandler.dao.JobOfferDAO;
import com.hr.recruitmenthandler.exceptionhandler.EntityNotFoundException;
import com.hr.recruitmenthandler.model.JobApplication;
import com.hr.recruitmenthandler.model.JobOffer;

@Service
public class JobOfferServiceImpl implements JobOfferService {

	private final JobOfferDAO jobOfferDAO;
	
	
	public JobOfferServiceImpl(final JobOfferDAO offerDAO) {
		this.jobOfferDAO = offerDAO;
	}
	
	/* (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.offer.JobOfferService#findAllOffers()
	 */
	public List<JobOffer> findAllOffers() throws EntityNotFoundException{
		List<JobOffer> offersLsit = (List<JobOffer>)jobOfferDAO.findAll();
		if (CollectionUtils.isEmpty(offersLsit)) {
			throw new EntityNotFoundException(JobOffer.class);
		}
		
		return offersLsit;
	}
	
	/* (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.offer.JobOfferService#findOfferByOfferId(java.lang.Long)
	 */
	public JobOffer findOfferByOfferId(Long offerId) throws EntityNotFoundException{
		JobOffer offer = jobOfferDAO.findOne(offerId);
		if (offer == null) {
			throw new EntityNotFoundException(JobOffer.class, "offerId", offerId.toString());
		}
		
		return offer;
	}
	
	/* (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.offer.JobOfferService#createNewOffer(com.hr.recruitmenthandler.model.JobOffer)
	 */
	public JobOffer createNewOffer(JobOffer offer) {
		
		/*If starting date is not provided then assume current date/time as starting date*/
		if (offer.getStartDate() == null) {
			offer.setStartDate(new Date());
		}
		
		offer.setNumberOfApplications(0);
		return jobOfferDAO.save(offer);
	}
	
	/**
	 * @param offerId
	 * @return
	 */
	public boolean updateOfferCounter(Long offerId) {
		return jobOfferDAO.incrementOfferCounter(offerId) > 0;
	}
	
	/* (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.offer.JobOfferService#isOfferExists(java.lang.String)
	 */
	public boolean isOfferExists(String jobTitle) {
		boolean isExists = false;
		JobOffer offer = jobOfferDAO.findOfferByJobTitle(jobTitle);
		isExists = offer != null;//if found an already persisted offer with same job title, set value to false
		
		return isExists;
	}
	
	/**
	 * @return
	 */
	public JobOfferDAO getJobOfferDAO() {
		return jobOfferDAO;
	}

}
