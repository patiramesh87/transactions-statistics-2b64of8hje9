package com.n26.transaction.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.transaction.exception.DateRangeException;
import com.n26.transaction.exception.ErrorCode;
import com.n26.transaction.model.Transaction;
import com.n26.transaction.repository.ITransactionRepository;

@Service
public class TransactionService {
	@Autowired
	private ITransactionRepository transactionRepository;
	
	private final Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	public void createTransaction(Transaction transaction) {
		validateTimestamp(transaction.getTimestamp());
		transactionRepository.create(transaction);
		logger.info("Create transaction " + transaction);
	}
	
	public void deleteTransactions() {
		transactionRepository.deleteAll();
		logger.info("deleted transaction ");
	}
	
	public List<Transaction> getTransactions(Instant start, Instant end) {
		return transactionRepository.findAllBetweenTimestamps(start, end);
	}
	
	private void validateTimestamp(Instant timestamp) {
		Instant now = Instant.now();
		if(timestamp.isBefore(now.minus(TransactionConstants.INTERVAL_DURATION_SEC, ChronoUnit.SECONDS))) {
			throw new DateRangeException(ErrorCode.OLDER_DATE, "Transaction date is older");
		} else if(timestamp.isAfter(now)) {
			throw new DateRangeException(ErrorCode.FUTURE_DATE, "Transaction date is in future");
		}
		
	}
	
}
