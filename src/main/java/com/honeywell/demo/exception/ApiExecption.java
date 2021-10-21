package com.honeywell.demo.exception;

public class ApiExecption extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ApiExecption(String errorMsg) {
		super(errorMsg);
	}

}
