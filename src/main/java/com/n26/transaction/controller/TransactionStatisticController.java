package com.n26.transaction.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transaction.model.TransactionStatistics;
import com.n26.transaction.service.TransactionStatisticsService;

@RestController
@RequestMapping("/statistics")
public class TransactionStatisticController {
	@Autowired
	private TransactionStatisticsService transactionStatisticsService;
	
	@GetMapping
	public ResponseEntity<TransactionStatistics> getTransactionStatics(){
		Instant instantOfRequest = Instant.now();
        TransactionStatistics transactionStatistics = transactionStatisticsService.
        		getStatistics(instantOfRequest.minus(1, ChronoUnit.MINUTES), instantOfRequest);
		return ResponseEntity.ok(transactionStatistics);
	}

}
