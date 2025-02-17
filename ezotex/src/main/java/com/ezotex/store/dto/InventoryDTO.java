package com.ezotex.store.dto;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryDTO {
	
	private String lot;
	private String productCode;
	private int storeQy;
	private int inventoryQy;
	private String productColor;
	private String productSize;
	private String storeCharger;
	private String storageLocation;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date storeDate;
	private String productSe;
	private String deliveryCode;
	
	// 제품명
	private String productName;
	
}
