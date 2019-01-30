package com.moneymoney.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.moneymoney.app.entity.Account;
import com.moneymoney.app.entity.CurrentAccount;
import com.moneymoney.app.entity.SavingsAccount;
@Service
public interface AccountService {
	
	

	List<Account> findAll();

	Optional<Account> findById(int accountId);

	void updateSavingsAccount(SavingsAccount account);

	void updateCurrentAccount(CurrentAccount account);

	void updateBalance(Account account);

}