package com.n26.transaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.n26.transaction.repository.ITransactionRepository;


@Component
public class TransactionRepositoryPurgeScheduler {
	@Autowired
	private ITransactionRepository transactionRepository;
	
	private final Logger logger = LoggerFactory.getLogger(TransactionRepositoryPurgeScheduler.class);
	
	//@Scheduled(cron="0/1 0 0 ? * * *")
	@Scheduled(fixedRate = 1000)
	public void scheduleTransactionDataPurge() {
      try {
    	  transactionRepository.purgeData();
      } catch (Exception ex) {
		logger.error("Exception during purge " + ex);
	}
   }
}
