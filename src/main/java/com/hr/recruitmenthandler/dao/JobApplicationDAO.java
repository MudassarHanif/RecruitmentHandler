package com.hr.recruitmenthandler.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hr.recruitmenthandler.model.JobApplication;

@Transactional
public interface JobApplicationDAO extends JpaRepository<JobApplication, Long>{

	@Query("SELECT a FROM JobApplication a WHERE LOWER(a.email) = LOWER(:email)")
	JobApplication findApplicationByEmail(@Param("email") String email);
	
	@Query("SELECT count(*) FROM JobApplication a JOIN a.jobOffer o WHERE o.offerId = :offerId AND LOWER(a.email) = LOWER(:email)")//TODO: and a.email to be changed...
	Integer isApplicationExists(@Param("email") String email, @Param("offerId") Long offerId);
	
	@Query("SELECT a FROM JobApplication a JOIN a.jobOffer o WHERE o.offerId = :offerId")
	List<JobApplication> findApplicationByOfferId(@Param("offerId") Long offerId);
	
}
