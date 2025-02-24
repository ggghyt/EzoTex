package com.ezotex.delivery.dto;

import lombok.Data;

/*
 * 납품관련 모든 담당자
 * */
@Data
public class DeliveryAllCharger {
	private String deliveryChargerCode;
	private String deliveryChargerName;
	private String deliveryRgsde;
	private String packingChargerCode;
	private String packingChargerName;
	private String packingRgsde;
	private String driverChargerCode;
	private String driverChargerName;
	private String driverChargerRgsde;
	private String driverCode;
	private String driverName;
	private String driveStartTime;
}
