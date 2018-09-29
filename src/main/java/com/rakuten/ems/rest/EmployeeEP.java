package com.rakuten.ems.rest;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rakuten.ems.config.RakutenConfiguration;
import com.rakuten.ems.domain.Employee;
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

	private static String DEFAULT_TEST_MESSAGE = "Hello, I am Alive";

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

	/**
	 * Upload File
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/upload")
	public List<Employee> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
		return employeeService.upload(multipartFile);
	}

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
	public List<Employee> getEmployees(@RequestParam(value = "erroredRecord", required = false) Boolean erroredRecord) {

		if (erroredRecord != null) {
			return employeeService.findByErroredRecord(erroredRecord);
		}
		return employeeService.findAll();
	}
}
