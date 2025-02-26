package com.ezotex.store.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DomListDTO {

	private int supplyPlanDetailCode;
	private String supplyPlanCode;
	private String productCode;
	private String productColor;
	private String productSize;
	private int supplyQy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date supplyDate;
	
}
