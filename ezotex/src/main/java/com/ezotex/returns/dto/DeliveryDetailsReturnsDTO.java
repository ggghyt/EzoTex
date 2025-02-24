package com.ezotex.returns.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DeliveryDetailsReturnsDTO {
	// 반품 , 교환할 제품 조회
	private int deliveryProductNo;
	private String deliveryCode;
	private String productCode;
	private String productSize;
	private String productColor;
	private String productLot;
	private int requestQy;
	private int deliveryQy;
	private int unitPrice;
	private String productSe;
	private String orderCode;
	private int price;
}
