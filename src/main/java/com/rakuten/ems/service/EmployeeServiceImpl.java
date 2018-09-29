package com.rakuten.ems.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.rakuten.ems.config.ApplicationConstants;
import com.rakuten.ems.config.RakutenConfiguration;
import com.rakuten.ems.domain.DesignationType;
import com.rakuten.ems.domain.Employee;
import com.rakuten.ems.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	/**
	 * employeeRepository
	 */
	@Inject
	private EmployeeRepository employeeRepository;

	/**
	 * rakutenConfiguration
	 */
	@SuppressWarnings("unused")
	@Inject
	private RakutenConfiguration rakutenConfiguration;

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public List<Employee> findByErroredRecord(boolean erroredRecord) {
		return employeeRepository.findByErroredRecord(erroredRecord);
	}

	@Override
	public List<Employee> upload(MultipartFile multipartFile) throws IOException {
		File file = convertMultiPartToFile(multipartFile);
		List<Employee> employeeList = null;
		try (Reader reader = new FileReader(file);) {

			@SuppressWarnings("unchecked")
			CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader).withType(Employee.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			employeeList = csvToBean.parse();
			// List<Employee> invalidEmpList = new ArrayList<>();

			Iterator<Employee> iterator = employeeList.iterator();
			while (iterator.hasNext()) {
				Employee employee = iterator.next();

				if (isInvalidName(employee.getName()) || isInvalidDepartment(employee.getDepartment())
						|| isInvalidDesignation(employee.getDesignation()) || isInvalidSalary(employee.getSalary())
						|| isInvalidJoiningDate(employee.getJoiningDate())) {
					employee.setErroredRecord(true);
					// In case incorrect data must not be stored
					// invalidEmpList.add(employee);
					// iterator.remove();
				}
			}
			employeeRepository.saveAll(employeeList);
			employeeList.forEach(
					emp -> LOGGER.info("Employee name: " + emp.getName() + "Errored Record: " + emp.isErroredRecord()));
		}

		return employeeList;
	}

	private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
		File convFile = new File(ApplicationConstants.UPLOAD_PATH + multipartFile.getOriginalFilename());
		convFile.getParentFile().mkdir();
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipartFile.getBytes());
		fos.close();
		return convFile;
	}

	// All Validation methods below to easily change the validation logic
	/**
	 * Name: can only contain alphabets
	 * 
	 * @param name
	 * @return
	 */
	private boolean isInvalidName(String name) {
		if (StringUtils.isAlphaSpace(name)) {
			return false;
		}
		LOGGER.info("Name is Invalid: ", name);
		return true;
	}

	/**
	 * Department: alphanumeric with -_* as special characters
	 * 
	 * @param department
	 * @return
	 */
	private boolean isInvalidDepartment(String department) {
		// rakutenConfiguration.getDepartmentPattern(); can be used
		Pattern regex = Pattern.compile(ApplicationConstants.DEPARTMENT_PATTERN);
		Matcher matcher = regex.matcher(department);
		if (matcher.find()) {
			return false;
		}
		LOGGER.info("Department is Invalid: ", department);
		return true;
	}

	/**
	 * Designation: Developer,Senior Developer,Manager,Team Lead, VP,CEO
	 * 
	 * @param designation
	 * @return
	 */
	private boolean isInvalidDesignation(String designation) {
		try {
			if (designation != null && DesignationType.getType(designation.trim()) != null) {
				return false;
			}
		} catch (IllegalArgumentException exception) {
			LOGGER.error("Designation is Invalid: ", designation);
		}
		return true;
	}

	/**
	 * Salary: can only contain Numeric value
	 * 
	 * @param salary
	 * @return
	 */
	private boolean isInvalidSalary(String salary) {
		if (salary != null && StringUtils.isNumeric(salary.trim())) {
			return false;
		}
		LOGGER.info("salary is Invalid: ", salary);
		return true;
	}

	/**
	 * Joining Date: joining date -> yyyy-MM-dd format
	 * 
	 * @param date
	 * @return
	 */
	private boolean isInvalidJoiningDate(String date) {
		if (date != null) {
			try {
				// rakutenConfiguration.getDateFormat() can be used as well
				LocalDate.parse(date.trim(), new DateTimeFormatterBuilder()
						.appendPattern(ApplicationConstants.DATE_FORMAT).parseStrict().toFormatter());
				return false;
			} catch (DateTimeParseException e) {
				LOGGER.info("Invalid date: " + date);
			}
		}

		return true;
	}
}
