package com.ezotex.delivery.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderProductDeliveryDTO {
	//주문코드, 납품코드, 업체코드, 구매 업체명, 요약, 상태, 납품담당자, 주문일, 납기일
	private String productOrderCode;
	private String deliveryCode;
	private String companyCode;
	private String company;
	private String Summary;
	private String orderCharger;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date orderDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dedt;
	private String orderStatus;
	
}
