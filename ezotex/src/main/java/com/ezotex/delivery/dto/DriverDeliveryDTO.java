package com.ezotex.delivery.dto;

import lombok.Data;

/*
 * 배송기사와 관련된 DTO
 * */
@Data
public class DriverDeliveryDTO {
	private String dedtAddress;
	private String companyName;
	private String deliveryStatus;
}
