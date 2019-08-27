package com.n26.transaction.repository;

import java.time.Instant;
import java.util.List;

import com.n26.transaction.model.Transaction;


public interface ITransactionRepository {
	public void purgeData();
	
	void create(Transaction transaction);

    void deleteAll();

    List<Transaction> findAllBetweenTimestamps(Instant start, Instant end);

    List<Transaction> findTransactionsByTimestamp(Instant instant);
}
