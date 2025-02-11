package com.ezotex.supply.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BomDTO {
	private String bomCode;
	private String productCode;
	private String productColor;
	private String productSize;
	private String chargerCode;
	private String chargetName;
	private Date registerDate;
	
	private String bomDetailCode;
	private String mtrilCode;
	private Integer requireQy;
}
