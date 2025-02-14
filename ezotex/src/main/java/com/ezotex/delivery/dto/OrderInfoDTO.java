package com.ezotex.delivery.dto;

import lombok.Data;

@Data
public class OrderInfoDTO {
	private String charger;
	private Integer reqQy;
	private Integer amount;
	private String address;
	private String productCode;
	private String productName;
}
