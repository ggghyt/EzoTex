package com.ezotex.returns.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DeliveryReturnsDTO {
	// 출고 조회
	private String deliveryCode;
	private String companySe;
	private String productOrderCode;
	private String mtrilOrderCode;
	private String companyCode;
	private String companyName;
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
	private Date rgsde;
	private Date updde;
	private int time;
	private int amount;
	private String deliveryCompanyCode;
}
