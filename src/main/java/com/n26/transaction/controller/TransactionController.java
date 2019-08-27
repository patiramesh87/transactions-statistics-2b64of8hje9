package com.n26.transaction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transaction.model.Transaction;
import com.n26.transaction.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<?> createTransaction(@Valid @RequestBody Transaction transaction){
		transactionService.createTransaction(transaction);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTransaction(){
		transactionService.deleteTransactions();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
