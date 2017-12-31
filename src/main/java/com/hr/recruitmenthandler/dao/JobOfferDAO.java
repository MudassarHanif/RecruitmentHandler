package com.hr.recruitmenthandler.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hr.recruitmenthandler.model.JobOffer;

@Transactional
public interface JobOfferDAO extends JpaRepository<JobOffer, Long>{

	@Query("SELECT o FROM JobOffer o WHERE LOWER(o.jobTitle) = LOWER(:jobTitle)")
	JobOffer findOfferByJobTitle(@Param("jobTitle") String jobTitle);
	
	@Query("UPDATE JobOffer o set numberOfApplications = numberOfApplications + 1 WHERE o.offerId = :offerId")
	@Modifying
	Integer incrementOfferCounter(@Param("offerId") Long offerId);
	
//	@Query("SELECT a FROM JobOffer a WHERE a.offerId = :offerId")
//	List<JobOffer> findOfferByOfferId(@Param("offerId") Long offerId);
//	
}
