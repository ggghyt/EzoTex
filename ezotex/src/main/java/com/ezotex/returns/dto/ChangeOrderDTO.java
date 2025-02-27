package com.ezotex.returns.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ChangeOrderDTO {
	// 교환 주문 등록
	private String productOrderCode;
	private String companyName;
	private String companyCharger;
	private String companyPhone;
	private String charger;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deliveryRequestDeadline;
	private String orderStatus;
	private String companyCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date requestDate;
	private String empCode;
	private String remarks;
	private int amount;
	
	// 교환 주문 제품 등록
	private String orderDetailCode;
	private String productCode;
	private String productColor;
	private String productSize;
	private int qy;
	private int unitPrice;
}
