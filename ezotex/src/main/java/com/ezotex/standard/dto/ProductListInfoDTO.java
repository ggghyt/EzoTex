package com.ezotex.standard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListInfoDTO {
	private String productCode;
	private String productName;
	private String productType;
	private String lclas;
	private String sclas;
	private String unitPrice;
	private String vl;
}
