package com.moneymoney.app.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moneymoney.app.entity.Account;
import com.moneymoney.app.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountResources {

@Autowired
private AccountService service;

	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> account = service.findAll();
		return new ResponseEntity<List<Account>>(account, HttpStatus.OK);
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccountById(@PathVariable int accountNumber) {
		Optional<Account> account = service.findById(accountNumber);
		if (account.get() == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Account>(HttpStatus.OK);

	}

	@GetMapping("/{accountNumber}/balance")
	public ResponseEntity<Double> getCurrentBalance(@PathVariable int accountNumber) {
		Optional<Account> account = service.findById(accountNumber);
		Double currentBalance = account.get().getCurrentBalance();
		if (account.get().getCurrentBalance() == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Double>(currentBalance, HttpStatus.OK);
	}

	@PutMapping("/{accountNumber}")
	public ResponseEntity<Account> updateAccountBalance(@PathVariable int accountNumber,
			@RequestParam Double currentBalance) {
		Optional<Account> optional = service.findById(accountNumber);
		Account account = optional.get();
		/*
		 * if (account == null) { return new ResponseEntity<>(null,
		 * HttpStatus.NOT_FOUND); }
		 */
		/* currentBalance+=accounts.getCurrentBalance(); */
		account.setCurrentBalance(currentBalance);
		service.updateBalance(account);
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
}