package com.ezotex.supply.vo;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SampleSupplyVO {
	private String bomCode;
	private String productCode;
	private String color;
	private String size;
	private String chargerCode;
	private String chargerName;
	private Date registerDate;
}
