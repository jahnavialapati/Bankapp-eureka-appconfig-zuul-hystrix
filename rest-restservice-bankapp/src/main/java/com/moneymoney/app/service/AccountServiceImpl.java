package com.moneymoney.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneymoney.app.entity.Account;
import com.moneymoney.app.entity.CurrentAccount;
import com.moneymoney.app.entity.SavingsAccount;
import com.moneymoney.app.repository.AccountRepository;
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository repository;

	@Override
	public List<Account> findAll() {
		return  repository.findAll();
	}

	@Override
	public Optional<Account> findById(int accountNumber) {
		return repository.findById(accountNumber);
	}
	
	@Override
	public void updateSavingsAccount(SavingsAccount account) {
		repository.save(account);
	}

	@Override
	public void updateCurrentAccount(CurrentAccount account) {
		repository.save(account);
	}

	@Override
	public void updateBalance(Account account) {
		repository.save(account);
	}

}
