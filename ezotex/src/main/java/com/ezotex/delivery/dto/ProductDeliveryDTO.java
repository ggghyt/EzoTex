package com.ezotex.delivery.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDeliveryDTO {
	//주문코드, 납품코드, 업체코드, 구매 업체명, 요약, 상태, 납품담당자, 주문일, 납기일
	private String productOrderCode;
	private String deliveryCode;
	private String companyCode;
	private String company;
	private String Summary;
	private String orderStatus;
	private String charger;
	private Date orderDate;
	private Date deliveryRequestDeadline;
}
