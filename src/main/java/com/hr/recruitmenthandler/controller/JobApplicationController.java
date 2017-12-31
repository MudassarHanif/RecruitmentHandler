package com.hr.recruitmenthandler.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.hr.recruitmenthandler.constants.ApplicationStatus;
import com.hr.recruitmenthandler.exceptionhandler.EntityNotFoundException;
import com.hr.recruitmenthandler.model.JobApplication;
import com.hr.recruitmenthandler.service.application.JobApplicationService;
import com.hr.recruitmenthandler.util.JobApplicationError;


@RestController
@RequestMapping(value = "/applications", produces = { MediaType.APPLICATION_JSON_VALUE })
public class JobApplicationController {
	
	private static final Logger logger = LoggerFactory.getLogger(JobApplicationController.class);

	@Autowired
	JobApplicationService jobApplicationService;
	
	/**
	 * loads an application from db and returns formatted Json
	 * @param id
	 * @return JSON response of an entity with type JobApplication
	 * @throws EntityNotFoundException
	 */
	@RequestMapping(value = "/{applicationId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getApplication(@PathVariable(value="applicationId", required=true) Long id) throws EntityNotFoundException{
		logger.info("Retrieving JobApplication with application ID: " + id);
		JobApplication app = jobApplicationService.findApplicationByApplicationId(id);
		return new ResponseEntity<JobApplication>(app, HttpStatus.OK);
	}
	
	/**
	 * loads the list of all the applications from db and returns formatted Json array
	 * @return
	 * @throws EntityNotFoundException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<JobApplication>> listAllApplications() throws EntityNotFoundException {
        List<JobApplication> applications = jobApplicationService.findAllApplications();
        
        return new ResponseEntity<List<JobApplication>>(applications, HttpStatus.OK);
    }

	/**
	 * createss a new application
	 * 
	 * @param application
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> createApplication(@RequestBody @Valid JobApplication application, UriComponentsBuilder ucBuilder) {
		String email = application.getEmail();
		Long offerId = application.getOfferId();
		
		//check if there is already an application with the same email for this offer..
		if (jobApplicationService.isApplicationExists(email, offerId)) {
			logger.error("Unable to create application. An application with email {} already exist", application.getEmail());
			return new ResponseEntity<JobApplicationError>(new JobApplicationError("You have already applied for this Job."),HttpStatus.CONFLICT);
		}

		application = createApplication(application);

		//add link for self
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/application/{id}").buildAndExpand(application.getApplicationId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	/**
	 * update an application with the provided content. 
	 * @param applicationId
	 * @param application
	 * @param ucBuilder
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{applicationId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateApplication(@PathVariable(value="applicationId", required=true) Long applicationId, 
    			@RequestBody JobApplication application, UriComponentsBuilder ucBuilder) throws Exception {
		
		//setting application id to load required entity in service
        application.setApplicationId(applicationId);
		JobApplication updatedApplication = jobApplicationService.updateApplication(application);
      
		if (updatedApplication == null || updatedApplication.getApplicationId() == null) {
            logger.error("Unable to update application with id {} ", application.getApplicationId());
            return new ResponseEntity<JobApplicationError>(new JobApplicationError("Unable to update application."),HttpStatus.NOT_MODIFIED);
        }
		
       //add link for self
       HttpHeaders headers = new HttpHeaders();
       headers.setLocation(ucBuilder.path("/application/{id}").buildAndExpand(application.getApplicationId()).toUri());
       return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	/**
	 * creates a new job application
	 * @param application
	 * @return
	 */
	private JobApplication createApplication(JobApplication application) {
		application.setApplicationStatus(ApplicationStatus.APPLIED.name());
		return jobApplicationService.saveApplication(application);
 
	}
	
	public void setJobApplicationService(JobApplicationService jobApplicationService) {
		this.jobApplicationService = jobApplicationService;
	}
	
	

}
