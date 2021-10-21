package com.honeywell.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.honeywell.demo.exception.ApiExecption;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(ApiExecption.class)
	public ResponseEntity<String> globalExceptionHandler(ApiExecption apiException){
		return new ResponseEntity<String>("There is some issue in getting subscription details", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	


}
