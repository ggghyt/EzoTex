package com.ezotex.delivery.dto;

import lombok.Data;

@Data
public class DeliveryProductInfo {
	//제품 옵션 사이즈별로 수량 출력을 위한DTO
	
	private String orderCode;
	private String deliveryCode;
	private String productCode;
	private String	productSize;
	private String	targetSize;
	private String	showSize;
	private String productColor;
	private String productLot;
	private int deliveryQy;
	private int unitPrice;
	private String productSe;
	
	// 제품명
	private String productName;
	private int productQy;
	
	//잔여량
	private int reqQy;
	//사이즈
	private String sizeSort;
}
