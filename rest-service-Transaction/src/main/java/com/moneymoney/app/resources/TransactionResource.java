package com.moneymoney.app.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.moneymoney.app.entity.CurrentDataSet;
import com.moneymoney.app.entity.Transaction;
import com.moneymoney.app.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {

	@Autowired
	private TransactionService service;
	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/depositamount")
	public ResponseEntity<Transaction> deposit(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://bankapp/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = service.deposit(transaction.getAccountNumber(), transaction.getTransactionDetails(),
				currentBalance, transaction.getAmount());
		restTemplate.put(
				"http://bankapp/accounts/" + transaction.getAccountNumber() + "?currentBalance=" + updateBalance,
				null);
		return new ResponseEntity<Transaction>(HttpStatus.CREATED);
	}

	@PostMapping("/withdrawamount")
	public ResponseEntity<Transaction> withdraw(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://bankapp/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = service.withdraw(transaction.getAccountNumber(), transaction.getTransactionDetails(),
				currentBalance, transaction.getAmount());
		restTemplate.put(
				"http://bankapp/accounts/" + transaction.getAccountNumber() + "?currentBalance=" + updateBalance,
				null);
		return new ResponseEntity<Transaction>(HttpStatus.CREATED);
	}

	/*
	 * @GetMapping public CurrentDataSet getstatement(@RequestParam("offset") int
	 * offset, @RequestParam("size") int size) { int currentSize = size == 0 ? 5 :
	 * size; int currentOffset = offset == 0 ? 1 : offset; Link next =
	 * linkTo(methodOn(TransactionResource.class).getstatement(currentOffset +
	 * currentSize, currentSize)) .withRel("next"); Link previous = linkTo(
	 * methodOn(TransactionResource.class).getstatement(currentOffset - currentSize,
	 * currentSize)) .withRel("previous"); List<Transaction> transactions =
	 * service.getStatement(); List<Transaction> currentDataSet = new
	 * ArrayList<Transaction>(); for (int i = currentOffset - 1; i < currentSize +
	 * currentOffset - 1; i++) { Transaction transaction = transactions.get(i);
	 * currentDataSet.add(transaction); } CurrentDataSet dataSet = new
	 * CurrentDataSet(currentDataSet, next, previous); return dataSet; }
	 */

	@GetMapping("/statement")
	public ResponseEntity<CurrentDataSet> getStatement() {
		CurrentDataSet currentDataSet = new CurrentDataSet();
		List<Transaction> transactions = service.getStatement();
		currentDataSet.setTransactions(transactions);
		return new ResponseEntity<CurrentDataSet>(currentDataSet, HttpStatus.OK);
	}

}
