package com.example.ftms.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name= "accounts")
public class AccountEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
    @Column(nullable= false, unique=true)
	private String accountNumber;
	
    @Column(nullable= false)
	private String accountHolderName;
	
    @Column(nullable= false)
	private BigDecimal balance= BigDecimal.ZERO;
	
    @OneToMany(mappedBy= "account", cascade= CascadeType.ALL, orphanRemoval= true)
	private List<TransactionEntity> transactions;
    
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    public AccountEntity() {}

	/**
	 * @param accountNumber
	 * @param accountHolderName
	 * @param balance
	 */
	public AccountEntity(String accountNumber, String accountHolderName, BigDecimal balance) {
		
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<TransactionEntity> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionEntity> transactions) {
		this.transactions = transactions;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
    
    
}
