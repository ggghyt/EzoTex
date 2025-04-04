package com.ezotex.supply.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomDTO {
	//private String bomCode;
	private String productCode;
	private String productColor;
	private String productSize;
	private String chargerCode;
	private String chargerName;
	private Date rgsde;
	
	//private String bomDetailCode;
	private String mtrilCode;
	private Integer requireQy;
	private String mtrilColor;
	
	// bom 내부 검색용
	private String mtrilName;
	private String unitName;
	private String lclas;
	private String sclas;
}
