package com.rova.accountService.dto.transactionResponse;

public class Sort{
	private boolean unsorted;
	private boolean sorted;
	private boolean empty;

	public boolean isUnsorted(){
		return unsorted;
	}

	public boolean isSorted(){
		return sorted;
	}

	public boolean isEmpty(){
		return empty;
	}
}
