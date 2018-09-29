package com.rakuten.ems.config;

/**
 * <pre>
 * Constants across the application
 * 1. Ideal solution
 * we should configure these in config-server and read from there
 * 
 * 2. From application.properties
 * Configuration file used to read from application.properties
 * @see RakutenConfiguration
 * 
 * 3. Constant File
 * This file is the third approach
 * 
 * </pre>
 * 
 * @author Giriraj Vyas
 *
 */
public class ApplicationConstants {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEPARTMENT_PATTERN = "^[a-zA-Z0-9\\-*_\\s]+$";
	public static final String UPLOAD_PATH = "D://temp//";

}
