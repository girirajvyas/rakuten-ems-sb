package com.rakuten.ems.domain;

/**
 * Enum for Designation
 * 
 * @author Giriraj Vyas
 *
 */
public enum DesignationType {

	DEVELOPER("Developer"), 
	SENIOR_DEVELOPER("Senior Developer"), 
	MANAGER("Manager"), 
	TEAM_LEAD("Team Lead"),
	VP("VP"),
	CEO("CEO");

	String value;

	DesignationType(String value) {
		this.value = value;
	}

	/**
	 * Check if it is valid enum value
	 * 
	 * @param value
	 * @return
	 */
	public static DesignationType getType(String value) {
		for (DesignationType type : DesignationType.values()) {
			if (value != null && value.trim().equalsIgnoreCase(type.value.toString())) {
				return type;
			}
		}
		return null;
	}
}
