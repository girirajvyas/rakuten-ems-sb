package com.rakuten.ems.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Reads all the properties from application.properties <br/>
 * Helps in grouping all the config
 * 
 * @author Giriraj Vyas
 *
 */
@Configuration
@ConfigurationProperties(prefix = "rakuten.config")
public class RakutenConfiguration {

	private String dateFormat;
	private String uploadPath;
	private String departmentPattern;

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getDepartmentPattern() {
		return departmentPattern;
	}

	public void setDepartmentPattern(String departmentPattern) {
		this.departmentPattern = departmentPattern;
	}
}
