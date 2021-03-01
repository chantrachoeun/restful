package com.choeunchantra.service.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.choeunchantra.service.ui.model.responses.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(value = {UserServiceException.class})
	public ResponseEntity<Object> handleServiceException(UserServiceException exception, WebRequest request){
		ErrorMessage error = new ErrorMessage(new Date(), exception.getMessage());
		
		return new ResponseEntity<>(error , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleException(Exception exception, WebRequest request){
		ErrorMessage error = new ErrorMessage(new Date(), exception.getMessage());
		
		return new ResponseEntity<>(error , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
