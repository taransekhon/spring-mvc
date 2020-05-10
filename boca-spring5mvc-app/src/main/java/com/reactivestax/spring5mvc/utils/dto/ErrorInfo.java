package com.reactivestax.spring5mvc.utils.dto;

import java.util.List;

/**
 * Represents the container ErrorInfo object that will be returned to the
 * calling client whenever there's an error (business or system related) in the
 * called api
 */
public class ErrorInfo {

	private String url;
	private String httpStatusCode;
	private String statusCode;
	private String httpStatusMessage;
	private List<DetailedMessage> detailedMessages;

	/**
	 * Basic contructor for ErrorInfo class
	 */
	public ErrorInfo() {
		super();
	}

	public String getHttpStatusMessage() {
		return httpStatusMessage;
	}

	public void setHttpStatusMessage(String httpStatusMessage) {
		this.httpStatusMessage = httpStatusMessage;
	}

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(String httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public List<DetailedMessage> getDetailedMessages() {
		return detailedMessages;
	}

	public void setDetailedMessages(List<DetailedMessage> detailedMessages) {
		this.detailedMessages = detailedMessages;
	}

	/**
	 * 
	 * Represents the detailed message to be conveyed to the calling client.
	 * Explains the message, the actual object and its specific field that
	 * caused the error
	 */
	public static class DetailedMessage {
		private String message;
		private String object;
		private String field;

		/**
		 * Basic Contructor for DetailedMessage class
		 */
		public DetailedMessage() {
			super();
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getObject() {
			return object;
		}

		public void setObject(String object) {
			this.object = object;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}