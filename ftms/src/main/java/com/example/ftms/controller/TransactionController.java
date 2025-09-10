package com.example.ftms.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ftms.model.TransactionEntity;
import com.example.ftms.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService txnService;
	
	public TransactionController(TransactionService txnService) {
		this.txnService=txnService;
	}
	
	@PostMapping("/{accountId}")
	public TransactionEntity createTransaction(@RequestBody TransactionEntity txnEntity, @PathVariable Long id) {
		return txnService.createTransaction(txnEntity, id);
	}
	
	@GetMapping
	public List<TransactionEntity> getAllTransactions(){
		return txnService.getAllTransacions();
	}
	
	@GetMapping("/{id}")
	public TransactionEntity getTransactionById(@PathVariable Long id) {
		return txnService.getTransactionById(id);
	}
	@GetMapping("/account/{accountId}")
	public List<TransactionEntity> getTransactionsByAccount(@PathVariable Long accountId) {
	    return txnService.fetchTransactionsByAccount(accountId);
	}

	@GetMapping("/account/{accountId}/type/{type}")
	public List<TransactionEntity> getTransactionsByType(@PathVariable Long accountId, @PathVariable String type) {
	    return txnService.fetchByAccountAndType(accountId, type);
	}

	@GetMapping("/account/{accountId}/date-range")
	public List<TransactionEntity> getTransactionsByDateRange(
	        @PathVariable Long accountId,
	        @RequestParam String start,
	        @RequestParam String end) {

	    return txnService.getTransactionsByDateRange(
	            accountId,
	            LocalDateTime.parse(start),
	            LocalDateTime.parse(end)
	    );
	}

}
