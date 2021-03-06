package com.rakuten.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rakuten.ems.domain.Employee;

/**
 * Employee Repository
 * 
 * @author Giriraj Vyas
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findById(Long id);
	
	List<Employee> findByEmpId(String empId);
	
	List<Employee> findByErroredRecord(boolean erroredRecord);

	
}
