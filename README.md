# RecruitmentHandlerApplication
Build and Run Application
	
	
	1. clean/Build project with maven.
	2. Package application into an executable jar(run command mvn clean package). Spring Boot Application class is located at com\hr\recruitmenthandler\RecruitmenthandlerApplication.java
		This should generate jar file named "recruitmenthandler-0.0.1-SNAPSHOT.jar" in target folder under project direcotry.
	3. Open commandline and point to the directory where .jar file is placed.
	4. Run standalone jar with following command. 
		java -jar .\recruitmenthandler-0.0.1-SNAPSHOT.jar


Use following API functions exposed by this application:

1. Create New Offer
	METHOD: POST -> http://localhost:8080/offers/

	Request JSON:

	{
     "jobTitle": "Senior Software Developer",
     "startDate": "20-12-2014 02:30:00"
	}


2. Get a single Offer
	METHOD: GET -> http://localhost:8080/offers/1

3. List all offers
	METHOD: GET -> http://localhost:8080/offers/

4. Create New Application
	METHOD: POST -> http://localhost:8080/applications/

	Request JSON:
	{
	    "offerId": 1,
	    "email": "mudassar@hrapp.com",
	    "lastName": "hanif",
	    "firstName": "Mudassar",
	    "resumeText": "resume text sample 1"
	    
	}

5. Get single application
	METHOD: GET -> http://localhost:8080/applications/1

6. Update applicationo
	METHOD: PUT -> http://localhost:8080/applications/1

	REQUEST JSON:
	{
	    "offerId": 1,
	    "email": "mudassar123232@sfds.com",
	    "lastName": "hanif",
	    "firstName": "Mudassar",
	    "resumeText": "resume text sample 1",
	    "applicationStatus": "HIRED"
	    
	}	

7. List all applications per offer
	METHOD: GET -> http://localhost:8080/offers/1/applications

8.	List all applications
	METHOD: GET -> http://localhost:8080/applications/



