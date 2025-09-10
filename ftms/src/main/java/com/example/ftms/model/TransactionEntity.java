package com.example.ftms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="transactions")
public class TransactionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private BigDecimal amount;
	
	@Column(nullable=false)
	private String type;
	
	@Column(nullable=false)
	private LocalDateTime timestamp;
	
	@Column
	private String description;
	
	@ManyToOne
	@JoinColumn(name ="account_id", nullable= false)
	private AccountEntity account;
	
	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

	public TransactionEntity() {}
	
	public TransactionEntity(Long id, BigDecimal amount, String type, LocalDateTime timestamp, String description) {
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.timestamp = timestamp;
		this.description = description;
	}

	public void setId(Long id) {
		this.id= id;
	}
	public Long getId() {return id;}
	
	public void setAmount(BigDecimal amount) {this.amount= amount;}
	
	public BigDecimal getAmount() {return amount;}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
