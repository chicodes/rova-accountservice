package com.rova.accountService.dto.transactionResponse;

public class Pageable{
	private boolean paged;
	private int pageNumber;
	private int offset;
	private int pageSize;
	private boolean unpaged;
	private Sort sort;

	public boolean isPaged(){
		return paged;
	}

	public int getPageNumber(){
		return pageNumber;
	}

	public int getOffset(){
		return offset;
	}

	public int getPageSize(){
		return pageSize;
	}

	public boolean isUnpaged(){
		return unpaged;
	}

	public Sort getSort(){
		return sort;
	}
}
