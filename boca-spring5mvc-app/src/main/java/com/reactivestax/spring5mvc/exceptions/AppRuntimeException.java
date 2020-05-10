package com.reactivestax.spring5mvc.exceptions;


import com.reactivestax.spring5mvc.utils.MessageUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the main unchecked exception that all APPs should be throwing for any foreseen system related issues.
 * - which don't fit into data related issues.
 * Just so to avoid throwing a RuntimeException - which is not a good practise.
 */
public class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -2859292084648724403L;
	private final int id;
	private final Map<String, String> errorMap;

	
	public AppRuntimeException() {
		super();
		this.id = 0;
		this.errorMap = new HashMap<>();
	}

	public AppRuntimeException(int id) {
		this.id = id;
		this.errorMap = new HashMap<>();
	}

	public AppRuntimeException(Map<String, String> errorMap) {
		super();
		this.errorMap = errorMap;
		this.id = 0;
	}

	public AppRuntimeException(int id, Map<String, String> errorMap) {
		super();
		this.errorMap = errorMap;
		this.id = id;
	}
	
	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.id = 0;
		this.errorMap = new HashMap<>();
	}

	public AppRuntimeException(String message) {
		super(message);
		this.id = 0;
		this.errorMap = new HashMap<>();
	}

	public AppRuntimeException(Throwable cause) {
		super(cause);
		this.id = 0;
		this.errorMap = new HashMap<>();
	}

	public int getId() {
		return id;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}
	
	@Override
   	public String toString() {
   		return "AppRuntimeException [getId()=" + getId() + ", getErrorMap()=" + MessageUtils.printMapInJSONFormat(getErrorMap())
   				+ ", getMessage()="
   				+ getMessage() + ", getCause()=" + getCause() + ", getStackTrace()=" + Arrays.toString(getStackTrace())
   				+ "]";
   	}
	
	
}