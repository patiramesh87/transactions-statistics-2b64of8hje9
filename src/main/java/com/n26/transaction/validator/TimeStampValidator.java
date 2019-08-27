package com.n26.transaction.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeStampValidator implements ConstraintValidator<TimeStampConstraint, Date> {
	private static final String DATE_PATTERN = "YYYY-MM-DDThh:mm:ss.sssZ";
	@Override
	public void initialize(TimeStampConstraint constraint) {
    }
	
	@Override
	public boolean isValid(Date date, ConstraintValidatorContext context) {
		if(date !=null) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
	        try
	        {
	            sdf.setLenient(false);
	            Date d = sdf.parse(date.toString());
	            return true;
	        }
	        catch (ParseException e)
	        {
	            return false;
	        }
		}
		return false;
	}

}