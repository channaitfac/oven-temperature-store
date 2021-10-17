
#####################################################################################
               Oven Temperature Store Application 
                         Build System
                           Readme
#####################################################################################

    About application
    =================================
    * Oven Temperature Store will:
    
       - Provide the JWT token for already signup users.
	     HTTP method: POST
         Parameters: {"username" : "admin", "password" : "admin"}
	     URL: http://127.0.0.1:44391/oven-temperature-store/users/signin
    
       - Provide all Ovens details.
	     HTTP method: GET
	     URL: http://127.0.0.1:44391/oven-temperature-store/ovens
		 
       - Provide Temperatures for specific Oven.
	     HTTP method: POST
         Parameters: {"ovenId": "37"}
	     URL: http://127.0.0.1:44391/oven-temperature-store/ovens/temperature
		 
       - Provide Temperatures for Ovens for given time period.
	     HTTP method: POST
         Parameters: {"startDate": "2021-10-16 10:28:36", "endDate": "2021-10-16 23:28:38"} 
	     URL: http://127.0.0.1:44391/oven-temperature-store/oven-temperatures
		       
    Note:- User signup, Ovens and related Temperatures related data will populate on application startup. 	 
          

	Software versions used
	=================================
	* java-jdk-11
	* apache-maven-3.6.3
	* spring-boot 2.6.0-SNAPSHOT
	
	
	System configuration
	=================================
	* Configure the values in "$oven-temperature-store\src\main\resources\application.yml" 	
		

	System build
	=================================
	* Build system by with test cases: 
	   - Go to project root "$oven-temperature-store" and execute the 'mvn clean install' maven command
	   
	* Build system by skipping test cases: 
	   - Go to project root "$oven-temperature-store" and execute the 'mvn clean install -DskipTests' maven command
	
	
	System deployment
	=================================
	* Go to project root "$oven-temperature-store" and execute the REST service with "mvn spring-boot:run" maven command
	      
  
	System test with Postman
	=================================
	* Refer the Postman related steps show with images in "$oven-temperature-store\test\postman"
	* Import the Postman related scripts in "$oven-temperature-store\test\postman\scripts\oven-temperature-store.postman_collection.json"  
	