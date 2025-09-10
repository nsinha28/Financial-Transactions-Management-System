package com.example.ftms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ftms.model.AccountEntity;
import com.example.ftms.service.AccountService;

@RestController
@RequestMapping(path= "/api/accounts")
public class AccountController {

	private final AccountService accountServ;
	
	public AccountController(AccountService accountService) {
		this.accountServ=accountService;
	}
	
	@PostMapping
	public AccountEntity createAccount(AccountEntity account) {
		return accountServ.createAccount(account);
	}
	
	@GetMapping
	public List<AccountEntity> getAllAccounts(){
		return accountServ.getAllAccountsForUser();
	}
	
	@GetMapping("/{id}")
	public AccountEntity getAccountById(@PathVariable Long id) {
		return accountServ.getAccountById(id);
	}
}
