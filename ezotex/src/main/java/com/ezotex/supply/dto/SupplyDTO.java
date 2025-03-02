package com.ezotex.supply.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyDTO {
	private String supplyPlanCode;
	private Integer supplyYear;
	private String season;
	private String chargerCode;
	private String chargerName;
	private String remark;
	private Date rgsde;
	private Date updateDate;
	
	private String supplyPlanDetailCode;
	//private String supplyPlanCode;
	private String productCode;
	private String productColor;
	private String productSize;
	private Integer supplyQy;
	private Date supplyDate;
	
	private String summary; // 조회용
	private String productName;
	
}
