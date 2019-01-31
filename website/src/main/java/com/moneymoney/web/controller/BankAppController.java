package com.moneymoney.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.moneymoney.web.entity.CurrentDataSet;
import com.moneymoney.web.entity.Transaction;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
public class BankAppController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}

	@RequestMapping("/depositamount")
	public String depositForm() {
		return "DepositForm";
	}

	@RequestMapping("/deposit")
	@HystrixCommand(fallbackMethod = "depositnotavailable")
	public String deposit(@ModelAttribute Transaction transaction, Model model) {
		transaction.setTransactionDetails("Atm");
		restTemplate.postForEntity("http://zuul-proxy/transaction-service/transactions/depositamount", transaction, null);
		model.addAttribute("message", "Success!");
		return "DepositForm";
	}

	public String depositnotavailable(@ModelAttribute Transaction transaction, Model model) {
		String messagebody = "service not available";
		model.addAttribute("message", messagebody);
		return "DepositForm";
	}

	@RequestMapping("/withdrawamount")
	public String withdrawForm() {
		return "WithdrawForm";
	}

	@RequestMapping("/withdraw")
	@HystrixCommand(fallbackMethod = "withdrawnotavailable")
	public String withdraw(@ModelAttribute Transaction transaction, Model model) {
		restTemplate.postForEntity("http://zuul-proxy/transaction-service/transactions/withdrawamount", transaction, null);
		model.addAttribute("message", "Success!");
		return "WithdrawForm";
	}

	public String withdrawnotavailable(@ModelAttribute Transaction transaction, Model model) {
		String messagebody = "service not available";
		model.addAttribute("message", messagebody);
		return "WithdrawForm";
	}

	@RequestMapping("/fundtransfer")
	public String fundTransferForm() {
		return "FundTransferForm";
	}

	@RequestMapping("/fundtransfering")
	@HystrixCommand(fallbackMethod = "fundtransfernotavailable")
	public String fundtransfer(@RequestParam("senderaccountnumber") int senderaccountnumber,
			@RequestParam("receiveraccountnumber") int receiveraccountnumber, @RequestParam int amount,
			@ModelAttribute Transaction transaction, Model model) {
		transaction.setAccountNumber(senderaccountnumber);
		restTemplate.postForEntity("http://zuul-proxy/transaction-service/transactions/withdrawamount", transaction, null);
		transaction.setAccountNumber(receiveraccountnumber);
		restTemplate.postForEntity("http://zuul-proxy/transaction-service/transactions/depositamount", transaction, null);
		model.addAttribute("message", "Success!");
		return "FundTransferForm";
	}

	public String fundtransfernotavailable(@RequestParam("senderaccountnumber") int senderaccountnumber,
			@RequestParam("receiveraccountnumber") int receiveraccountnumber, @RequestParam int amount,@ModelAttribute Transaction transaction, Model model) {
		String messagebody = "service not available";
		model.addAttribute("message", messagebody);
		return "FundTransferForm";
	}

	@RequestMapping("/statementDeposit")
	public ModelAndView getStatementDeposit(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://zuul-proxy/transaction-service/transactions/statement",
				CurrentDataSet.class);
		int currentSize = size == 0 ? 5 : size;
		int currentOffset = offset == 0 ? 1 : offset;
		Link next = linkTo(
				methodOn(BankAppController.class).getStatementDeposit(currentOffset + currentSize, currentSize))
						.withRel("next");
		Link previous = linkTo(
				methodOn(BankAppController.class).getStatementDeposit(currentOffset - currentSize, currentSize))
						.withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) {
			if ((transactions.size() <= i && i > 0) || currentOffset < 1)
				break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		return new ModelAndView("DepositForm", "currentDataSet", dataSet);
	}

	@RequestMapping("/statementWithdraw")
	public ModelAndView getStatementWithdraw(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://zuul-proxy/transaction-service/transactions/statement",
				CurrentDataSet.class);
		int currentSize = size == 0 ? 5 : size;
		int currentOffset = offset == 0 ? 1 : offset;
		Link next = linkTo(
				methodOn(BankAppController.class).getStatementWithdraw(currentOffset + currentSize, currentSize))
						.withRel("next");
		Link previous = linkTo(
				methodOn(BankAppController.class).getStatementWithdraw(currentOffset - currentSize, currentSize))
						.withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) {
			if ((transactions.size() <= i && i > 0) || currentOffset < 1)
				break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		return new ModelAndView("WithdrawForm", "currentDataSet", dataSet);
	}

	@RequestMapping("/statementFundTransfer")
	public ModelAndView getStatementFundtransfer(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://zuul-proxy/transaction-service/transactions/statement",
				CurrentDataSet.class);
		int currentSize = size == 0 ? 5 : size;
		int currentOffset = offset == 0 ? 1 : offset;
		Link next = linkTo(
				methodOn(BankAppController.class).getStatementDeposit(currentOffset + currentSize, currentSize))
						.withRel("next");
		Link previous = linkTo(
				methodOn(BankAppController.class).getStatementDeposit(currentOffset - currentSize, currentSize))
						.withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) {
			if ((transactions.size() <= i && i > 0) || currentOffset < 1)
				break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		return new ModelAndView("DepositForm", "currentDataSet", dataSet);
	}

}
