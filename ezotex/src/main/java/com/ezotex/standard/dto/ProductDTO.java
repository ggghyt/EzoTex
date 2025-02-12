package com.ezotex.standard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {	
	
	private String productCode;
	private String productName;
	private String productType;
	private Integer unitPrice;
	private Integer vl;
	private String img;
	private String unitName;
	
	private String lclas;
	private String sclas;
	// private String productCode;
	
	private String optionCode;
	// private String productCode;
	private String productColor;
	private String productSize;
	// private Integer unitPrice;
	private String discontinued;
	
}
