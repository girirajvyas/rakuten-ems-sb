# rakuten-ems
Employee Management System(EMS) Spring Boot app

# Run Project
1. This project 1 part of 3 repositories for rakuten-ems:  
 Git Repositories locations:  
   https://github.com/girirajvyas/rakuten-ems-helpers  
   https://github.com/girirajvyas/rakuten-ems-npm  
   https://github.com/girirajvyas/rakuten-ems-sb  
 `git clone` all above repositories in rakuten-sb folder

3. Setup Backend  
   a. go to: rakuten-ems-sb  
   b. `mvn install`  
   c. `mvn spring-boot:run` (this will start server at default 8080 port)  
   
4. Setup UI  
   a. go to: rakuten-ems-npm  
   b. `npm install`  
   c. `ng serve -o` (this will build and open the browser @ http://localhost:4200)  
  
5. Configure UI  
   You can configure the project by updating the below properties in env.js file:  
   **File location**: rakuten-ems-npm\src\config   
   **Out of the box settings**:  
   
   
	   var envs = {  
		"APPLICATION_TITLE": "Rakuten Employee Management System",
		"BASE_URL": "http://localhost:8080",
		"UPLOAD_URL": "http://localhost:8080/upload",
		"UPLOAD_MAX_SIZE": "5",
		"UPLOAD_FORMATS_ALLOWED": ".csv",
		"ROWS_ON_PAGE": 5,
		"ROWS_ON_PAGE_SET": [5,10,15]
	  }
   

6. Configure Back-end  
   You can configure the project by updating the below properties in application.properties file:  
   **File location**: rakuten-ems-sb\src\main\resources\  
   **Out of the box settings**:  
   
   ```
    rakuten.config.dateFormat=yyyy-MM-dd
    rakuten.config.uploadPath=D://temp//
    rakuten.config.departmentPattern=^[a-zA-Z0-9\\-*_\\s]+$
   ```
