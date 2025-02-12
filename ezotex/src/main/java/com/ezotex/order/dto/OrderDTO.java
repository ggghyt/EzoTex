package com.ezotex.order.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDTO {
	// 주문
	private String productOrderCode;
	private String company;
	private String companyRepresentative;
	private String companyTel;
	private String charger;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deliveryRequestDeadline;
	private String orderStatus;
	private String companyCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date orderDate;
	private String empCode;
	
	//주문 제품
	private String orderDetailCode;
	private String productCode;
	private String productColor;
	private String productSize;
	private int qy;
	private int unitPrice;
	
	//제품코드 조회
	private String productName;
	private String productType;
	private int vl;
	private String img;
	private String unitName;
}
