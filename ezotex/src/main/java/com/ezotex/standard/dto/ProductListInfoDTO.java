package com.ezotex.standard.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListInfoDTO {
	private String productCode;
	private String productName;
	private String productTypeName;
	private String productType;
	private String lclas;
	private String sclas;
	private int unitPrice;
	private int vl;
	private String unitName;
	private String unitNameName;
	private String img;
	private MultipartFile imgFile;
	private int qy;
	
	private int maxPrice;
	private int minPrice;
}
