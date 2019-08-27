package com.n26.transaction.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.n26.transaction.model.Transaction;
import com.n26.transaction.model.TransactionStatistics;

@Component
public class TransactionStatisticsBuilder {
	
	public TransactionStatistics buildReport(List<Transaction> transactionList) {
		TransactionStatistics transactionStatistics = new TransactionStatistics();
		if(!CollectionUtils.isEmpty(transactionList)) {
	        BigDecimal[] transaction_amount_list = transactionList.stream().
	                map(a -> a.getAmount()).toArray(size -> new BigDecimal[size]);
	
	        BigDecimal sum = Arrays.stream(transaction_amount_list).reduce(
	                BigDecimal.ZERO, (t, u) -> t.add(u));
	        transactionStatistics.setSum(sum.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
	        transactionStatistics.setCount(transactionList.size());
	        transactionStatistics.setMax(Arrays.stream(transaction_amount_list)
	        		.max(Comparator.naturalOrder()).get().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
	        transactionStatistics.setMin(Arrays.stream(transaction_amount_list)
	        		.min(Comparator.naturalOrder()).get().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
	        transactionStatistics.setAvg(sum.divide(new BigDecimal(transactionStatistics.getCount()),2, RoundingMode.HALF_UP)
	        		.toString());
	        
		}
        
        return transactionStatistics;
	}
}
