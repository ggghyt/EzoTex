package com.ezotex.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
	
	// 변환된 사이즈값
	private String productSizeInfo;
	
	// 제품명
	private String productName;
	private int productQy;
	
	//사이즈 별 수량
	private int S;
	private int M;
	private int L;
	private int XL;
	
	public StoreDeliveryDetailsDTO() {}
	
	public StoreDeliveryDetailsDTO(String sizeCode, String size, String color, String productCode, String deliveryCode) {
	    this.productSize = sizeCode;
	    this.productColor = color;
	    this.productCode = productCode;
	    this.deliveryCode = deliveryCode;
	    this.productSizeInfo = size; // 입고수량
	}
	
}
