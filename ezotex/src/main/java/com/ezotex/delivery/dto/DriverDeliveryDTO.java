package com.ezotex.delivery.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/*
 * 배송기사와 관련된 DTO
 * */
@Data
public class DriverDeliveryDTO {
	private String dedtAddress;
	private String companyName;
	private String deliveryStatus;
	private String dedt;
	private String deliveryCode;
	private String chargerCode;		//배송기사 코드
	private String chargerName;		//배송기사 이름
	private String imgUrl;
	private MultipartFile imgFile;
}
