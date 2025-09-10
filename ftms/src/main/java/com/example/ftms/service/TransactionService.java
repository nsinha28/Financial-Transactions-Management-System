package com.example.ftms.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.ftms.model.AccountEntity;
import com.example.ftms.model.TransactionEntity;
import com.example.ftms.model.UserEntity;
import com.example.ftms.repository.AccountRepository;
import com.example.ftms.repository.TransactionRepository;
import com.example.ftms.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {
	
	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	
	public TransactionService(TransactionRepository transactionReository, AccountRepository accountRepo, UserRepository userRepository) {
		this.transactionRepository=transactionReository;
		this.accountRepository= accountRepo;
		this.userRepository=userRepository;
	}
	
	@Transactional
	public TransactionEntity createTransaction(TransactionEntity transaction, Long accountId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    UserEntity user = userRepository.findByUsername(username);
	    
		AccountEntity account= accountRepository.findById(accountId).
				orElseThrow(() -> new RuntimeException("Account name not found with id:" +accountId));
		
		 if (!account.getUser().getId().equals(user.getId())) {
		        throw new RuntimeException("You are not authorized to access this account");
		    }
		//link transaction with account
		transaction.setAccount(account);
		transaction.setTimestamp(LocalDateTime.now());
		
		//update balance
		BigDecimal amount= transaction.getAmount();
		if("CREDIT" .equalsIgnoreCase(transaction.getType())) {
			account.setBalance(account.getBalance().add(amount));
		}
		if("DEBIT" .equalsIgnoreCase(transaction.getType())) {
			if(account.getBalance().compareTo(amount)< 0) {
				throw new RuntimeException("Insufficient balance");
			}
			account.setBalance(account.getBalance().subtract(amount));
		}
		else {
			throw new RuntimeException("Invalid transaction type. Use CREDIT or DEBIT.");
		}
		//save updated account & transaction
		accountRepository.save(account);
		return transactionRepository.save(transaction);
	}
//	public TransactionEntity saveTransaction(TransactionEntity transaction) {
//		return transactionRepository.save(transaction);
//	}
	
	public List<TransactionEntity> fetchTransactionsByAccount(Long accountId){
		AccountEntity account= accountRepository.findById(accountId).orElseThrow(()-> new RuntimeException("Account not found"));
		return transactionRepository.findByAccount(account);
	}
	
	public List<TransactionEntity> fetchByAccountAndType(Long actId, String type){
		AccountEntity account= accountRepository.findById(actId).orElseThrow(()-> new RuntimeException("Account not found"));
		return transactionRepository.findByAccountAndType(account, type.toUpperCase());
	}
	
	public List<TransactionEntity> getTransactionsByDateRange(Long accountId, LocalDateTime start, LocalDateTime end) {
	    AccountEntity account = accountRepository.findById(accountId)
	            .orElseThrow(() -> new RuntimeException("Account not found"));
	    return transactionRepository.findByAccountAndTimestampBetween(account, start, end);
	}
	
	public TransactionEntity getTransactionById(Long id) {
		return transactionRepository.findById(id).orElse(null);
	}
	
	public List<TransactionEntity> getAllTransacions(){
		return transactionRepository.findAll();
	}

}
