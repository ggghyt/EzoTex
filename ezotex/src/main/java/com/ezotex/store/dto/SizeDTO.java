package com.ezotex.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SizeDTO {

	private String productCode;
	private String color;
	private String sizeCode;
	private int productQy;
	private String deliveryCode;
	private String returnCode;
	
	// 창고 상세 코드
	private String storageLocation;
	
	// 상태 변환을 위한 요청량, 입고수량 비교 필드
	private int requestQy;
	private int totalQy;
	
	// 불량수량
	private int errorQy;
	private String lot;
	private String location;
	
	
	
	
}
