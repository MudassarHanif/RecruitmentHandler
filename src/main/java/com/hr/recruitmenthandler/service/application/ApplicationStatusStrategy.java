package com.hr.recruitmenthandler.service.application;

import com.hr.recruitmenthandler.model.JobApplication;

public enum ApplicationStatusStrategy {

	APPLIED{

		@Override
		void changeApplicationStatus(JobApplication application) throws Exception {
			System.out.print("Application Status changed to APPLIED...");
			
		}
		
	},
	
	INVITED{

		@Override
		void changeApplicationStatus(JobApplication application) throws Exception {
			System.out.print("Application Status changed to INVITED...");
			
		}
		
	},
	
	HIRED{

		@Override
		void changeApplicationStatus(JobApplication application) throws Exception {
			System.out.print("Application Status changed to HIRED...");
			
		}
		
	},
	
	REJECTED{

		@Override
		void changeApplicationStatus(JobApplication application) throws Exception {
			System.out.print("Application Status changed to REJECTED...");
			
		}
		
	};
	
	abstract void changeApplicationStatus( JobApplication application) throws Exception;
}
