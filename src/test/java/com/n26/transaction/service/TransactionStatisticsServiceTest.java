package com.n26.transaction.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.n26.transaction.model.Transaction;
import com.n26.transaction.model.TransactionStatistics;

@RunWith(MockitoJUnitRunner.class)
public class TransactionStatisticsServiceTest {

	@InjectMocks
	private TransactionStatisticsService transactionStatisticsService;
	
	@Mock
	private TransactionStatisticsBuilder transactionStatisticsBuilder;
	
	@Mock
	private TransactionService transactionService;
	
	@Test
	public void testGetStatistics() {
		
		List<Transaction> transactionList = new ArrayList<>();
		TransactionStatistics transactionStatistics = new TransactionStatistics();
		transactionStatistics.setAvg("8.98");
		transactionStatistics.setCount(3);
		transactionStatistics.setMax("45");
		
		when(transactionService.getTransactions(Instant.now().minusSeconds(80), Instant.now())).thenReturn(
				transactionList);
		
		when(transactionStatisticsBuilder.buildReport(transactionList)).thenReturn(transactionStatistics);
		
		TransactionStatistics result = transactionStatisticsService.getStatistics(Instant.now().minusSeconds(80), Instant.now());
		assertTrue(result.equals(transactionStatistics));
	}
}
