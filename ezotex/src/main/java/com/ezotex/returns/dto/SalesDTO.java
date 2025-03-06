package com.ezotex.returns.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SalesDTO {
	// PRODUCT_INFO 제품명
	private String productName;
	// ORDER_LIST 주문등록일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")	
	private Date orderDate;
	// ORDER_PRODUCT 컬럼
	private String orderDetailCode;
	private String productOrderCode;
	private String productCode;
	private String productColor;
	private String productSize;
	private int totalQy;
	private int unitPrice;
	// 총금액
	private int amount;
	
	// 주문코드에 대한 매출액과 손실액 조회
	private int totalSales;
	private int totalLoss;
	private int totalOrderQuantity;
	private int totalReturnQuantity;
}
