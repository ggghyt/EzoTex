package com.ezotex.supply.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialOrderDTO {
	private String mtrilOrderCode;
	private String companyCode;
	private String companyName;
	private Date dueDate;
	private String chargerCode;
	private String chargerName;
	private String remark;
	private Date rgsde;
	private String status;
	
	private String mtrilOrderDetailCode;
	//private String mtrilOrderCode;
	private String productCode;
	private Integer orderQy;
	private Integer amount;
	private String productColor;
	private Integer unitPrice;
}
