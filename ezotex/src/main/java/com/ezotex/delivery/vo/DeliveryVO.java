package com.ezotex.delivery.vo;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryVO {
	private String bomCode;
	private String productCode;
	private String color;
	private String size;
	private String chargerCode;
	private String chargerName;
	private Date registerDate;
}
