package com.hr.recruitmenthandler.service.application;

import java.util.List;

import com.hr.recruitmenthandler.exceptionhandler.EntityNotFoundException;
import com.hr.recruitmenthandler.model.JobApplication;

public interface JobApplicationService {

	List<JobApplication> findAllApplications() throws EntityNotFoundException;
	JobApplication findApplicationByApplicationId(Long applicatoinId) throws EntityNotFoundException;
	List<JobApplication> findApplicationByOfferId(Long offerId) throws EntityNotFoundException;
	JobApplication saveApplication(JobApplication application);
	Boolean isApplicationExists(String email, Long offerId);
	JobApplication updateApplication(JobApplication application) throws Exception;
	
	
}
