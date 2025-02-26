package com.ezotex.standard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StorageProductDTO {
	private String lot;
	private String storageInfoCode;
	private String optionCode;
	private String productCode;
	private String productName;
	private String storeDate;
	private String storeChargerName;
	private String storeChargerCode;
	private String qy;
}
