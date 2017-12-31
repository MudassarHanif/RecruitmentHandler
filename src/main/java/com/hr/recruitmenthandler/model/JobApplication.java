package com.hr.recruitmenthandler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "job_application", schema = "recruitmentdb")
public class JobApplication {
	private Long applicationId;
	private String lastName;
	private String firstName;
	private String email;
	private String resumeText;
	private String applicationStatus;
	
	private JobOffer jobOffer;
	
	
	public JobApplication() {}
	
	public JobApplication(Long offerId, String candidateEmail, String resumeText,
			String applicationStatus) {
		super();
		setOfferId(offerId);
		this.email = candidateEmail;
		this.resumeText = resumeText;
		this.applicationStatus = applicationStatus;
	}
	
	public JobApplication(Long applicationId, Long offerId, String candidateEmail, String resumeText,
			String applicationStatus) {
		super();
		this.applicationId = applicationId;
		setOfferId(offerId);
		this.email = candidateEmail;
		this.resumeText = resumeText;
		this.applicationStatus = applicationStatus;
	}
	
	@Id
    @GeneratedValue
    @Column(name="application_id", nullable=false)
    public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

    @Transient
	public Long getOfferId() {
    	if (jobOffer == null) {
    		return null;
    	}
    	
		return jobOffer.getOfferId();
	}

	public void setOfferId(Long offerId) {
		if (offerId == null) {
			jobOffer = null;
		}else {
			jobOffer = new JobOffer();
			jobOffer.setOfferId(offerId);
		}
	}


	@Column(name="email")
	@NotNull
    public String getEmail() {
		return email;
	}

	public void setEmail(String candidateEmail) {
		this.email = candidateEmail;
	}

	@Column(name="resume_text")
    public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	@Column(name="application_status")
    public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	@Column(name="last_name")
    public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name="first_name")
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@ManyToOne
    @JoinColumn(name="offer_id", nullable=false)
    @JsonIgnore
	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
	
	

}
