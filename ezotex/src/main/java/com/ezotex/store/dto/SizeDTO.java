package com.ezotex.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SizeDTO {

	// 사이즈별 수량
	private String sizeS;
	private String sizeM;
	private String sizeL;
	private String sizeXL;
	private String sizeFREE;
	private String sizeXS;
	// 사이즈별 수량 end
	private String name;
	private String productColor;
	private String productCode;
	// 사이즈 코드
	private String sizeCode;
	// 납품 코드
	private String deliveryCode;
	
	public SizeDTO() {}
	
	 // 수동으로 생성자 추가
    public SizeDTO(String sizeCode, String size, String productColor, String productCode, String name) {
        this.sizeCode = sizeCode;
        this.sizeS = size;
        this.productColor = productColor;
        this.productCode = productCode;
        this.name = name;
    }
	
	
}
