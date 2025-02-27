package com.ezotex.delivery.dto;

import lombok.Data;

/*
 * 배송기사 검색DTO
 */
@Data
public class DriverDeliverySearchDTO {
	
	int start;
	int end;
	
	private String dedtAddress;
	private String companyName;
	private String deliveryStatus;
}
