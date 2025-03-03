package com.ezotex.delivery.dto;

import lombok.Data;


/*
 * (제조업체 사용)제품 상세 정보
 * */
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
	//수량 합꼐
	private int qySum;
	//사이즈
	private String sizeSort;
	//부피
	private int vl;
}
