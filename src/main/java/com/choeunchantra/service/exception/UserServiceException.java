package com.choeunchantra.service.exception;

public class UserServiceException extends RuntimeException{
	private static final long serialVersionUID = -3292298903403687113L;

	public UserServiceException(String message) {
		super(message);
	}
}
