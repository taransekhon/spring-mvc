package com.reactivestax.spring5mvc.exceptions;

import com.reactivestax.spring5mvc.utils.MessageUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * This class represents all cases of invalidData - be it in inputDataValidation failure
 * or business rules validation failure.
 */
public class InvalidDataException extends AppRuntimeException {

    private static final long serialVersionUID = -2859292084648724403L;

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(Map<String,String> errorMap) {
        super(errorMap);
    }

	public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(Throwable cause) {
        super(cause);
    }
    @Override
   	public String toString() {
   		return "InvalidDataException [getId()=" + getId() + ", getErrorMap()=" + MessageUtils.printMapInJSONFormat(getErrorMap())
   				+ ", getMessage()="
   				+ getMessage() + ", getCause()=" + getCause() + ", getStackTrace()=" + Arrays.toString(getStackTrace())
   				+ "]";
   	}
}