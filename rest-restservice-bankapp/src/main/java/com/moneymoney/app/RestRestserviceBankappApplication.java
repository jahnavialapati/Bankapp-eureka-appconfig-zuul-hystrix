package com.moneymoney.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.moneymoney.app.entity.SavingsAccount;
import com.moneymoney.app.repository.AccountRepository;

@SpringBootApplication
public class RestRestserviceBankappApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestRestserviceBankappApplication.class, args);
	}
	@Bean
	public CommandLineRunner populateData(AccountRepository repository) {	
		return (evt) -> {
			repository.save(new SavingsAccount(101,"Jahnavi",false));
			repository.save(new SavingsAccount(102,"Saroja",10_000.00,true));
			repository.save(new SavingsAccount(103,"Ramu",true));
			repository.save(new SavingsAccount(104,"siva",20_000.00,true));
			repository.save(new SavingsAccount(105,"priya",25_000.00,true));
			repository.save(new SavingsAccount(106,"sam",57_000.00,true));
			repository.save(new SavingsAccount(107,"siri",15_000.00,true));
			repository.save(new SavingsAccount(108,"riya",15_000.00,true));
			repository.save(new SavingsAccount(109,"teja",20_000.00,true));
			repository.save(new SavingsAccount(110,"rama",30_000.00,true));
		};
	}
}

