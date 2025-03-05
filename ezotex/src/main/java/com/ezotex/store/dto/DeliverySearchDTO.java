package com.ezotex.store.dto;

import lombok.Data;

@Data
public class DeliverySearchDTO {

	int start;
	int end;
	
	String productCode;
	String productName;
	String startUnitPrice;
	String endUnitPrice;
	String se;
	String color;
	String sizeCode;
	
	String deliveryCode;
	String startOrderDate;
	String endOrderDate;
	String startDedt;
	String endDedt;
	
	
	String returnCode;
	String requestor;
	String startRequestDate;
	String endRequestDate;
	
	String startStoreDate;
	String endStoreDate;
	
	String location;
	String searchLot;
	String searchColor;
	
	String startErrorDate;
	String endErrorDate;
	
	
}
