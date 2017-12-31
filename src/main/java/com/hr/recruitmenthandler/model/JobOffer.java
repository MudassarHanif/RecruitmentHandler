package com.hr.recruitmenthandler.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "job_offer", schema = "recruitmentdb")
public class JobOffer {
	private Long offerId;
	private String jobTitle;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date startDate;
	private Integer numberOfApplications;
	private Set<JobApplication> jobApplications;
	
	public JobOffer() {
	}

	
	public JobOffer(Long offerId, Date startDate, String jobTitle, Integer numberOfApplications) {
		super();
		this.offerId = offerId;
		this.startDate = startDate;
		this.jobTitle = jobTitle;
		this.numberOfApplications = numberOfApplications;
	}


	@Id
    @GeneratedValue
    @Column(name="offer_id", nullable=false)
	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	@Column(name="start_date")
	@NotNull
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	@Column(name="job_title")
	@NotNull
	public String getJobTitle() {
		return jobTitle;
	}


	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}


	@Column(name="no_of_applications", columnDefinition="Decimal(3.0) default '0'")
	public Integer getNumberOfApplications() {
		return numberOfApplications;
	}

	public void setNumberOfApplications(Integer numberOfApplications) {
		this.numberOfApplications = numberOfApplications;
	}


	@OneToMany(mappedBy="jobOffer")
	@JsonIgnore
	public Set<JobApplication> getJobApplications() {
		return jobApplications;
	}


	public void setJobApplications(Set<JobApplication> jobApplications) {
		this.jobApplications = jobApplications;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
