package com.ezotex.order.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDTO {
	private String productOrderCode;
	private String company;
	private String companyRepresentative;
	private String companyTel;
	private String charger;
	private Date deliveryRequestDeadline;
	private String orderStatus;
}
