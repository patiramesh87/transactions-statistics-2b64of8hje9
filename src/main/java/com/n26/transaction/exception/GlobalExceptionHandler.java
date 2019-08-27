package com.n26.transaction.exception;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class, MismatchedInputException.class})
    public void handleBindingErrors(Exception ex) {
		logger.error(ex.getMessage());
    }
	
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler({com.fasterxml.jackson.core.JsonParseException.class, 
		org.springframework.boot.json.JsonParseException.class, InvalidFormatException.class})
    public void handleParsingErrors(Exception ex) {
		logger.error(ex.getMessage());
    }
	
	@ExceptionHandler(DateRangeException.class)
	  public final ResponseEntity<?> handleDateRangeException(DateRangeException ex) {
		logger.error(ex.getMessage());
		if(ex.getCode()==ErrorCode.OLDER_DATE.getCode()) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	  }
}
