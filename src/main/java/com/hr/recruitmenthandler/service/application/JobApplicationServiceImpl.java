package com.hr.recruitmenthandler.service.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hr.recruitmenthandler.dao.JobApplicationDAO;
import com.hr.recruitmenthandler.dao.JobOfferDAO;
import com.hr.recruitmenthandler.exceptionhandler.EntityNotFoundException;
import com.hr.recruitmenthandler.model.JobApplication;

@Service
@Transactional
public class JobApplicationServiceImpl implements JobApplicationService {

	private final JobApplicationDAO jobApplicationDAO;
	private final JobOfferDAO jobOfferDAO;
	
	
	/**
	 * @param applicationDAO
	 * @param jobOfferDAO
	 */
	public JobApplicationServiceImpl(final JobApplicationDAO applicationDAO, final JobOfferDAO jobOfferDAO) {
		this.jobApplicationDAO = applicationDAO;
		this.jobOfferDAO = jobOfferDAO;
	}
	
	/* (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.application.JobApplicationService#findAllApplications()
	 */
	public List<JobApplication> findAllApplications() throws EntityNotFoundException{
		List<JobApplication> applicationsList = (List<JobApplication>)jobApplicationDAO.findAll();
		if(CollectionUtils.isEmpty(applicationsList)){
            throw new EntityNotFoundException(JobApplication.class);
        }
	
		return applicationsList;
	}
	
	/* (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.application.JobApplicationService#findApplicationByOfferId(java.lang.Long)
	 */
	public List<JobApplication> findApplicationByOfferId(Long offerId) throws EntityNotFoundException{
		List<JobApplication> applicationsList = jobApplicationDAO.findApplicationByOfferId(offerId);
		if(CollectionUtils.isEmpty(applicationsList)){
            throw new EntityNotFoundException(JobApplication.class, "offerId", offerId.toString());
        }
	
		return applicationsList;
	}
	
	/* (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.application.JobApplicationService#findApplicationByApplicationId(java.lang.Long)
	 */
	public JobApplication findApplicationByApplicationId(Long applicatoinId) throws EntityNotFoundException{
		JobApplication application = jobApplicationDAO.findOne(applicatoinId);
		if (application == null) {
			throw new EntityNotFoundException(JobApplication.class, "applicatoinId", applicatoinId.toString());
		}
		
		return application;
	}
	
	/* (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.application.JobApplicationService#saveApplication(com.hr.recruitmenthandler.model.JobApplication)
	 */
	public JobApplication saveApplication(JobApplication application) {
		application = jobApplicationDAO.save(application);
		jobOfferDAO.incrementOfferCounter(application.getOfferId());
		return application;
	}
	
	/* Update an appliactions with any changes in property values. If status changes, then handle specific business cases
	 * (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.application.JobApplicationService#updateApplication(com.hr.recruitmenthandler.model.JobApplication)
	 */
	public JobApplication updateApplication(JobApplication application) throws Exception {
		JobApplication applicationToUpdate = jobApplicationDAO.findOne(application.getApplicationId());
		if (applicationToUpdate == null) {
			return null;
		}
		
		/* 
		 * Use Strategy pattern to execute business cases based on application status changes. 
		 * Although many other solutions can be applied in this scenario, a simple enum strategy interface will suffice here.
		 * */
		if (! application.getApplicationStatus().equalsIgnoreCase(applicationToUpdate.getApplicationStatus())) {
			ApplicationStatusStrategy.valueOf(application.getApplicationStatus()).changeApplicationStatus(application);
		}
		
		applicationToUpdate.setApplicationStatus(application.getApplicationStatus());
		applicationToUpdate.setResumeText(application.getResumeText());
		applicationToUpdate.setFirstName(application.getFirstName());
		applicationToUpdate.setLastName(application.getLastName());
		applicationToUpdate.setEmail(application.getEmail());
		
		applicationToUpdate = jobApplicationDAO.save(applicationToUpdate);
		
		return applicationToUpdate;
		
	}
	
	/* (non-Javadoc)
	 * @see com.hr.recruitmenthandler.service.application.JobApplicationService#isApplicationExists(java.lang.String, java.lang.Long)
	 */
	public Boolean isApplicationExists(String email, Long offerId) {
		Integer count = jobApplicationDAO.isApplicationExists(email, offerId);
		return count > 0;
	}
	
	/**
	 * @return
	 */
	public JobApplicationDAO getJobApplicationDAO() {
		return jobApplicationDAO;
	}
	
	

}
