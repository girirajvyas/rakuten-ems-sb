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

	public List<Employee> findAll();

	public List<Employee> findByErroredRecord(boolean erroredRecord);

	public List<Employee> upload(MultipartFile multipartFile) throws IOException;

}
