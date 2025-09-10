package com.example.ftms.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.ftms.model.AccountEntity;
import com.example.ftms.model.UserEntity;
import com.example.ftms.repository.AccountRepository;
import com.example.ftms.repository.UserRepository;

@Service
public class AccountService {private final AccountRepository accountRepository;
private final UserRepository userRepository;

public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
}

public AccountEntity createAccount(AccountEntity account) {
    // Get logged-in username
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository.findByUsername(username);

    account.setUser(user);
    account.setBalance(BigDecimal.ZERO);
    return accountRepository.save(account);
}

public List<AccountEntity> getAllAccountsForUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository.findByUsername(username);
    return accountRepository.findByUser(user);
}

public AccountEntity getAccountById(Long id) {
    return accountRepository.findById(id).orElse(null);
}
}
