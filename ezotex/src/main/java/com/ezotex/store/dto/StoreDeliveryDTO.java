package com.ezotex.store.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StoreDeliveryDTO {
 
	private String deliveryCode;
	private String companySe;
	private String productOrderCode;
	private String mtrilOrderCode;
	private String orderEnterpriseCode;
	private String orderEnterpriseName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date orderDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dedt;
	private String storageName;
	private String dedtAddress;
	private String deliveryStatus;
	private String remark;
	private String chargerCode;
	private String chargerName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date rgsde;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updde;
	private int time;
	private int deliveryQy;
	
}
