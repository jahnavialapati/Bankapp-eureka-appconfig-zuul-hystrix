package com.moneymoney.app.service;

import java.time.LocalDate;
import java.util.List;

import com.moneymoney.app.entity.Transaction;

public interface TransactionService {

	Double withdraw(int accountNumber, String transactioDetails, double currentBalance, double amount);

	Double deposit(int accountNumber, String transactioDetails, double currentBalance, double amount);

	Double[] fundTransfer(int senderAccountNumber, String transactioDetails, double currentBalance,
			int recieverAccountNumber, double amount);

	List<Transaction> getStatement(LocalDate startDate, LocalDate endDate);

	List<Transaction> getStatement();

}
