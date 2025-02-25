package com.ezotex.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SizeDTO {

	private String productCode;
	private String color;
	private String sizeCode;
	private String productQy;
	private String deliveryCode;
	
	// 상태 변환을 위한 요청량, 입고수량 비교 필드
	private int requestQy;
	private int totalQy;
	
}
