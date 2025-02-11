package com.ezotex.store.dto;


import java.util.Date;


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
	private Date storeDate;
	private String productSe;
	private String deliveryCode; 
}
