package com.ezotex.store.dto;

import lombok.Data;

@Data
public class ProductInfoListDTO {

	private String productCode;
	private String productName;
	private String productType;
	private int unitPrice;
	private int vl;
	private String img;
	private String unitName;
	
	// 색상
	private String productColor;
	// 사이즈 
	private String productSize;
	
	
}
