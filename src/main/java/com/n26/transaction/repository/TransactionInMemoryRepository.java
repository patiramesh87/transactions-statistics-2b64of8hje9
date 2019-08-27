package com.n26.transaction.repository;

import java.lang.ref.SoftReference;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.n26.transaction.exception.InfraException;
import com.n26.transaction.model.Transaction;
import com.n26.transaction.service.TransactionConstants;

@Repository
public class TransactionInMemoryRepository implements ITransactionRepository{

	private final Logger logger = LoggerFactory.getLogger(TransactionInMemoryRepository.class);
    private final ConcurrentNavigableMap<Long, SoftReference<List<Transaction>>> transactionLookUpStore = new ConcurrentSkipListMap<>();
    private final DelayQueue<DelayedCacheObject> cleaningUpQueue = new DelayQueue<>();
    
    @Override
    public void purgeData() {
    	while(!cleaningUpQueue.isEmpty()) {
	    	try {
	            DelayedCacheObject delayedCacheObject = cleaningUpQueue.take();
	            transactionLookUpStore.remove(delayedCacheObject.getKey(), delayedCacheObject.getReference());
	
	            logger.info("removing transaction {} ", delayedCacheObject.getKey());
	        } catch (InterruptedException ex) {
	        	throw new InfraException(ex);
	        }
    	}
    }

    @Override
    public void create(Transaction transaction) {
        transactionLookUpStore.computeIfAbsent(transaction.getTimestamp().toEpochMilli(),
                k -> {
                    SoftReference<List<Transaction>> softReference = new SoftReference<>(Collections.synchronizedList(new ArrayList<>()));
                    cleaningUpQueue.put(new DelayedCacheObject(transaction.getTimestamp().toEpochMilli(),
                            softReference, System.currentTimeMillis() + TransactionConstants.INTERVAL_DURATION_SEC*1000));
                    return softReference;
                }).get().add(transaction);

    }

    @Override
    public void deleteAll() {
        cleaningUpQueue.clear();
        transactionLookUpStore.clear();
    }

    @Override
    public List<Transaction> findAllBetweenTimestamps(Instant start, Instant end) {

        if (start.isAfter(end))
            return null;
        return transactionLookUpStore
                .subMap(start.toEpochMilli(), end.toEpochMilli())
                .values()
                .parallelStream()
                .flatMap(i -> {
                    synchronized (i) {
                        return i.get().stream();
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findTransactionsByTimestamp(Instant instant) {
        SoftReference<List<Transaction>> reference = transactionLookUpStore.get(instant.toEpochMilli());
        return reference != null ? reference.get() : new ArrayList<>();
    }

    private static class DelayedCacheObject implements Delayed {

        private final Long key;
        private SoftReference<List<Transaction>> reference;
        private final long expiryTime;

        private DelayedCacheObject(Long key, SoftReference<List<Transaction>> reference, long expiryTime) {
            this.key = key;
            this.reference = reference;
            this.expiryTime = expiryTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(expiryTime, ((DelayedCacheObject) o).expiryTime);
        }

        public Long getKey() {
            return key;
        }

        public SoftReference<List<Transaction>> getReference() {
            return reference;
        }
    }

}
