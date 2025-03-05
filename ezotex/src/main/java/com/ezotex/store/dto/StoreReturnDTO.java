package com.ezotex.store.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StoreReturnDTO {
	
	private String returnCode;
	private String deliveryCode;
	private String companyCode;
	private String companyName;
	private String requestor;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate requestDate;
	
	// 제품코드
	private String productCode;
	// 제품이름
	private String productName;
	
	// 제품수량 카운터
	private int count;
	// 반품요청수량
	private int qy;
	// 미처리 수량
	private int notStoreQy;
	// 처리완료수량
	private int storeQy;
	// 사이즈
	private String productSizeInfo;
	
}
