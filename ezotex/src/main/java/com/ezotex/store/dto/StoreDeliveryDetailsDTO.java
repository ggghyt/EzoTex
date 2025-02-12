package com.ezotex.store.dto;

import lombok.Data;

@Data
public class StoreDeliveryDetailsDTO {

	// 납품 상세조회 필드
	private int deliveryProductNo;
	private String deliveryCode;
	private String productCode;
	private String	productSize;
	private String productColor;
	private String productLot;
	private int requestQy;
	private int deliveryQy;
	private int unitPrice;
	private String productSe;
	
	// 제품명
	private String productName;
	
}
