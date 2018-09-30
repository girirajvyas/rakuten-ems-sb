package com.rakuten.ems.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rakuten.ems.domain.Employee;

/**
 * Employee Service
 * 
 * @author Giriraj Vyas
 *
 */
public interface EmployeeService {

	public List<Employee> findAll(Boolean erroredRecord);

	public Employee findById(Long id);
	
	public List<Employee> findByEmpId(String empId);
	
	public List<Employee> findByErroredRecord(boolean erroredRecord);

	public Employee save(Employee employee);
	
	public List<Employee> upload(MultipartFile multipartFile) throws IOException;

}
