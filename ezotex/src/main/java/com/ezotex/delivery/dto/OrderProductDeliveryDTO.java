package com.ezotex.delivery.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * 납품 관리 페이지 검색 결과 화면 출력용 DTO (공급업체, 제조업체 둘 다 사용)
 * */
@Data
@NoArgsConstructor
public class OrderProductDeliveryDTO {
	
	private String productOrderCode;
	private String mtrilOrderCode;
	private String deliveryCode;
	private String companyCode;
	private String company;
	private String Summary;
	private String orderCharger;
	private String orderChargerCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date orderDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dedt;
	private String orderStatus;
	private String deliveryStatus;
	private String remark;
	private Integer time;
	private String registDate;
	private String storageName;
	private String dedtAddress;
	private String commStatus;
}
