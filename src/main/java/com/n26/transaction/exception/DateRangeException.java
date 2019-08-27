package com.n26.transaction.exception;

public class DateRangeException extends RuntimeException{
	
	private static final long serialVersionUID = 9015646861777064664L;
	private int code;

	public DateRangeException(ErrorCode code, String msg) {
		super(msg);
		this.code = code.getCode();
	}
	
    public DateRangeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.code = errorCode.getCode();
    }
    
	public int getCode() {
		return code;
	}
	
}
