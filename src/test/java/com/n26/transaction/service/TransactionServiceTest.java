package com.n26.transaction.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.n26.transaction.exception.DateRangeException;
import com.n26.transaction.exception.InfraException;
import com.n26.transaction.model.Transaction;
import com.n26.transaction.repository.ITransactionRepository;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
	@InjectMocks
	private TransactionService transactionService;
	
	@Mock
	private ITransactionRepository transactionRepository;
	
	@Test(expected = DateRangeException.class)
	public void testCreateTransactionHappenedBeforeInterval() {
		Transaction transaction = new Transaction();
		transaction.setAmount(new BigDecimal(30));
		transaction.setTimestamp(Instant.now().minusSeconds(61));
		
		transactionService.createTransaction(transaction);
	}
	
	@Test(expected = DateRangeException.class)
	public void testCreateTransactionHappenedAfterInterval() {
		Transaction transaction = new Transaction();
		transaction.setAmount(new BigDecimal(30));
		transaction.setTimestamp(Instant.now().plusSeconds(61));
		
		transactionService.createTransaction(transaction);
	}
	
	@Test
	public void testCreateTransaction() {
		Transaction transaction = new Transaction();
		transaction.setAmount(new BigDecimal(30));
		transaction.setTimestamp(Instant.now().minusSeconds(11));
		doNothing().when(transactionRepository).create(transaction);
		transactionService.createTransaction(transaction);
	}
	
	@Test(expected = InfraException.class)
	public void testCreateTransactionWhenRepositoryDown() {
		Transaction transaction = new Transaction();
		transaction.setAmount(new BigDecimal(30));
		transaction.setTimestamp(Instant.now().minusSeconds(11));
		doThrow(new InfraException("repo down")).when(transactionRepository).create(transaction);
		 
		transactionService.createTransaction(transaction);
	}
	
	@Test
	public void testdeleteTransactions() {
		doNothing().when(transactionRepository).deleteAll();
		transactionService.deleteTransactions();
	}

}
