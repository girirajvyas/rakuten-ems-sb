package com.rakuten.ems.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Entity for Employee
 * 
 * @author Giriraj Vyas
 *
 */
@Entity
@JsonInclude(Include.NON_EMPTY)
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// kept String as sometimes have letter
	private String empId;
	private String name;
	private String department;
	private String designation;
	private String salary;
	private String joiningDate;
	private boolean erroredRecord;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public boolean isErroredRecord() {
		return erroredRecord;
	}

	public void setErroredRecord(boolean erroredRecord) {
		this.erroredRecord = erroredRecord;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + id + ", name=" + name + ", department=" + department + ", designation="
				+ designation + ", salary=" + salary + ", joiningDate=" + joiningDate + "]";
	}

}
