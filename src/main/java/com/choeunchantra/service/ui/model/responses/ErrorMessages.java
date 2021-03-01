package com.choeunchantra.service.ui.model.responses;

public enum ErrorMessages {
	MISSING_REQUIRED_FIELD("Missing required fields. Please check documentation for required fields." ),
	RECORD_ALREADY_EXISTS("record already exists."),
	INTERNAL_SERVER_ERROR("Internal server error."),
	NO_RECORD_FOUND("Record with provided id is not found."),
	AUTHENTICATION_FAILED("Authentication failed."),
	COULD_NOT_UPDATE_RECORD("Could not update record."),
	COULD_NOT_DELETE_RECORD("Could not delete record."),
	EMAIL_ADDRES_NOT_VERFIED("Email address could not be verified.");
	
	private String errorMessage;
	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
}
