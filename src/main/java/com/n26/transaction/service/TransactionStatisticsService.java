package com.n26.transaction.service;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.transaction.model.Transaction;
import com.n26.transaction.model.TransactionStatistics;

@Service
public class TransactionStatisticsService {
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionStatisticsBuilder transactionStatisticsBuilder;
	
	private final Logger logger = LoggerFactory.getLogger(TransactionStatisticsService.class);
	
	public TransactionStatistics getStatistics(Instant start, Instant end) {
		List<Transaction> transactionList = transactionService.getTransactions(start, end);
		return transactionStatisticsBuilder.buildReport(transactionList);
	}
}
