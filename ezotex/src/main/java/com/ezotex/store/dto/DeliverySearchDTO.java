package com.ezotex.store.dto;

import lombok.Data;

@Data
public class DeliverySearchDTO {

	int start;
	int end;
	
	String productCode;
	String  color;
	String sizeCode;
	
}
