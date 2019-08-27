package com.n26.transaction.exception;

public enum ErrorCode {
	OLDER_DATE(422),
	FUTURE_DATE(423);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
