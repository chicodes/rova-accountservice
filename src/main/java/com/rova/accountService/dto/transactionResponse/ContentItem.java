package com.rova.accountService.dto.transactionResponse;

public class ContentItem{
	private String transactionType;
	private Object amount;
	private String dateCreated;
	private Object balanceBefore;
	private String customerId;
	private Object balanceAfter;
	private int id;
	private Object dateUpdated;

	public String getTransactionType(){
		return transactionType;
	}

	public Object getAmount(){
		return amount;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public Object getBalanceBefore(){
		return balanceBefore;
	}

	public String getCustomerId(){
		return customerId;
	}

	public Object getBalanceAfter(){
		return balanceAfter;
	}

	public int getId(){
		return id;
	}

	public Object getDateUpdated(){
		return dateUpdated;
	}
}
