package com.rakuten.ems.rest;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rakuten.ems.config.RakutenConfiguration;
import com.rakuten.ems.domain.Employee;
import com.rakuten.ems.exception.CustomErrorType;
import com.rakuten.ems.service.EmployeeService;

/**
 * Employee End-Point
 * 
 * @author Giriraj Vyas
 *
 */
@CrossOrigin(origins = "*")
@RestController
public class EmployeeEP {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeEP.class);

	/**
	 * employeeService
	 */
	@Inject
	private EmployeeService employeeService;

	/**
	 * rakutenConfiguration
	 */
	@Inject
	private RakutenConfiguration rakutenConfiguration;

	// remove with "/test" end-point
	private static String DEFAULT_TEST_MESSAGE = "Hello, I am Alive";

	/**
	 * Get All Employee Records (Valid / Invalid)
	 * 
	 * A Query parameter can be passed to specify if only specific type of
	 * record is required
	 * 
	 * @param erroredRecord
	 * @return
	 */
	@GetMapping(value = "/employees")
	public ResponseEntity<List<Employee>> getEmployees(
			@RequestParam(value = "erroredRecord", required = false) Boolean erroredRecord) {
		List<Employee> employees = null;
		employees = employeeService.findAll(erroredRecord);

		if (employees != null && !employees.isEmpty()) {
			return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
		}

		return new ResponseEntity<List<Employee>>(employees, HttpStatus.NO_CONTENT);
	}

	/**
	 * Get Employee by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/employees/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable("id") Long id) {
		LOGGER.info("Fetching Employee with id {}", id);
		Employee employee = employeeService.findById(id);
		
		if (employee == null) {
			return new ResponseEntity<>(new CustomErrorType("No Employee found with id = " + id),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(employeeService.save(employee), HttpStatus.OK);
	}

	/**
	 * Update employee based on id
	 * 
	 * @param id
	 * @param employee
	 * @return
	 */
	@PutMapping(value = "/employees/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
		LOGGER.info("Fetching Employee with id {}", id);
		Employee currentEmployee = employeeService.findById(id);
		
		if (currentEmployee == null) {
			return new ResponseEntity<>(new CustomErrorType("Unable to Update, No Employee with id = " + id),
					HttpStatus.NOT_FOUND);
		}
		 
		return new ResponseEntity<>(employeeService.save(employee), HttpStatus.OK);
	}

	/**
	 * Upload File
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/upload")
	public ResponseEntity<List<Employee>> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile)
			throws IOException {
		List<Employee> uploadedEmployees = employeeService.upload(multipartFile);
		// HttpStatus should be CREATED but for npm upload api it is made OK
		return new ResponseEntity<List<Employee>>(uploadedEmployees, HttpStatus.OK);
	}
	

	/**
	 * Must be removed.
	 * 
	 * @return
	 */
	@GetMapping(value = "/test")
	public String test() {
		LOGGER.info("Date Format: " + rakutenConfiguration.getDateFormat());
		LOGGER.info("Department Pattern: " + rakutenConfiguration.getDepartmentPattern());
		LOGGER.info("Upload Path: " + rakutenConfiguration.getUploadPath());
		return DEFAULT_TEST_MESSAGE;
	}
}
