package com.n26.transaction.exception;

public class InfraException extends RuntimeException {
	
	private static final long serialVersionUID = -6680178091148722101L;

	public InfraException(String arg0) {
		super(arg0);
	}

	public InfraException(Throwable cause) {
		super(cause);
	}

	public InfraException(String message, Throwable cause) {
		super(message, cause);
	}

	public InfraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
