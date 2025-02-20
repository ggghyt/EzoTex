package com.ezotex.store.dto;

import lombok.Data;

@Data
public class ErrorProductDTO {

	private String productCode;
	private int errorQy;
	private String errorInfo;
	private String processDate;
	private String errorCharger;
	private String lot;
	private String productColor;
	private String productSize;
	
}
