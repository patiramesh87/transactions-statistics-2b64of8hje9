package com.n26.transaction.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class Transaction implements Serializable{
	
	private static final long serialVersionUID = 8453752650634236301L;

	@NotNull
	private BigDecimal amount;
	
	@NotNull
	@DateTimeFormat(pattern="YYYY-MM-DDThh:mm:ss.sssZ")
	private Instant timestamp;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	
	 @Override
	    public String toString() {
	        return "Transaction{" +
	                " amount='" + amount + '\'' +
	                ", timestamp=" + timestamp +
	                '}';
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Transaction that = (Transaction) o;
	        return Objects.equals(amount, that.amount) &&
	                Objects.equals(timestamp, that.timestamp);
	    }

	    @Override
	    public int hashCode() {

	        return Objects.hash(amount, timestamp);
	    }
}
