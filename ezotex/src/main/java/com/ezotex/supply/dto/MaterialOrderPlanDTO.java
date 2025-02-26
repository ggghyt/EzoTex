package com.ezotex.supply.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialOrderPlanDTO {
	private String mtrilOrderPlanCode;
	private Date dueDate;
	private String chargerCode;
	private String chargerName;
	private String remark;
	private Date rgsde;
	private Date updateDate;
	private String status;
	
	private String mtrilOrderPlanDetailCode;
	//private String mtrilOrderPlanCode;
	private String companyCode;
	private String companyName;
	private String productCode;
	private String productColor;
	private Integer orderQy;
	
	// 조회용 필드
	private String summary; // 함수로 임의 생성한 값
	private String unitName;
	private Integer unitPrice;
	private String productName;
}
