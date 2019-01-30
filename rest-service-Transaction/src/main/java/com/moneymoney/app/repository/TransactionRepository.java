package com.moneymoney.app.repository;

import java.time.LocalDate;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



import com.moneymoney.app.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	public List<Transaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
}
