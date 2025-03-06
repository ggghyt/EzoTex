package com.ezotex.store.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorProductDTO {

	private String productCode;
	private int errorQy;
	private String errorInfo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate processDate;
	private String errorCharger;
	private String lot;
	private String productColor;
	private String productSize;
	private String deliveryCode;
	private String productName;
	
	
	private double totalErrorQy;
	
}
