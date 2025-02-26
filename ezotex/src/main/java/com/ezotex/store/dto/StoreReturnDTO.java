package com.ezotex.store.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StoreReturnDTO {
	
	private String return_code;
	private String delivery_code;
	private String company_code;
	private String company_name;
	private String requestor;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date request_date;
	
}
