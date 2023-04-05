package com.rova.accountService.dto.transactionResponse;

import com.rova.accountService.dto.transactionResponse.RespBody;
import lombok.Data;

@Data
public class TransactionResponseDto {
	private RespBody respBody;
	private String respDescription;
	private String respCode;
}
