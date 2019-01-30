package com.moneymoney.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.moneymoney.app.entity.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account,Object>{

	
}
