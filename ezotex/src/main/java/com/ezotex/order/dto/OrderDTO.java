package com.ezotex.order.dto;

import java.util.Date;

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
	private Date deliveryRequestDeadline;
	private String orderStatus;
	private String companyCode;
	private Date orderDate;
	private String empCode;
	
	//주문 제품
	private String orderDetailCode;
	private String productCode;
	private String productColor;
	private String productSize;
	private int qy;
	private int unitPrice;
}
