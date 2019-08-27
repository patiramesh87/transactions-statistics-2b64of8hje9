package com.n26.transaction.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.n26.transaction.model.Transaction;
import com.n26.transaction.model.TransactionStatistics;

@RunWith(MockitoJUnitRunner.class)
public class TransactionStatisticsBuilderTest {
	@InjectMocks
	private TransactionStatisticsBuilder transactionStatisticsBuilder;
	
	@Test
	public void testBuildReportDuringNoTransaction() {
		List<Transaction> transactionList = new ArrayList<>();
		TransactionStatistics result = transactionStatisticsBuilder.buildReport(transactionList);
		assertTrue(result.equals(new TransactionStatistics()));
		
		result = transactionStatisticsBuilder.buildReport(null);
		assertTrue(result.equals(new TransactionStatistics()));
	}
	
	@Test
	public void testBuildReport() {
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction1 = new Transaction();
		transaction1.setAmount(new BigDecimal(127.96));
		transaction1.setTimestamp(Instant.now());
		
		Transaction transaction2 = new Transaction();
		transaction2.setAmount(new BigDecimal(262.01));
		transaction2.setTimestamp(Instant.now());
		
		Transaction transaction3 = new Transaction();
		transaction3.setAmount(new BigDecimal(456.011));
		transaction3.setTimestamp(Instant.now());
		
		transactionList.add(transaction1);
		transactionList.add(transaction2);
		transactionList.add(transaction3);
		
		TransactionStatistics result = transactionStatisticsBuilder.buildReport(transactionList);
		assertEquals("281.99", result.getAvg());
		assertEquals("845.98", result.getSum());
		assertEquals("456.01", result.getMax());
		assertEquals("127.96", result.getMin());
		assertEquals(3, result.getCount());
	}

}
