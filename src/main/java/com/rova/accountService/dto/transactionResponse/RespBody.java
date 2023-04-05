package com.rova.accountService.dto.transactionResponse;

import java.util.List;

public class RespBody{
	private int number;
	private boolean last;
	private int numberOfElements;
	private int size;
	private int totalPages;
	private Pageable pageable;
	private Sort sort;
	private List<ContentItem> content;
	private boolean first;
	private int totalElements;
	private boolean empty;

	public int getNumber(){
		return number;
	}

	public boolean isLast(){
		return last;
	}

	public int getNumberOfElements(){
		return numberOfElements;
	}

	public int getSize(){
		return size;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public Pageable getPageable(){
		return pageable;
	}

	public Sort getSort(){
		return sort;
	}

	public List<ContentItem> getContent(){
		return content;
	}

	public boolean isFirst(){
		return first;
	}

	public int getTotalElements(){
		return totalElements;
	}

	public boolean isEmpty(){
		return empty;
	}
}