package com.reactivestax.spring5mvc.exceptions;

import com.reactivestax.spring5mvc.utils.MessageUtils;

import java.util.Arrays;

/**
 * This class represents the case when there's not data found for a given parameter. 
 *
 */
public class NoDataFoundException extends AppRuntimeException {

    private static final long serialVersionUID = -2859292084648724403L;

    public NoDataFoundException(int id) {
        super(id);
    }

    public NoDataFoundException() {
        super();
    }

    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDataFoundException(String message) {
        super(message);
    }

    public NoDataFoundException(Throwable cause) {
        super(cause);
    }
    @Override
   	public String toString() {
   		return "NoDataFoundException [getId()=" + getId() + ", getErrorMap()=" + MessageUtils.printMapInJSONFormat(getErrorMap())
   				+ ", getMessage()="
   				+ getMessage() + ", getCause()=" + getCause() + ", getStackTrace()=" + Arrays.toString(getStackTrace())
   				+ "]";
   	}
}