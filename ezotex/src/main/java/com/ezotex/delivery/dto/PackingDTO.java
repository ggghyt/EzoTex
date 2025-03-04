package com.ezotex.delivery.dto;

import lombok.Data;

/*
 * 포장등록에 필요한 정보
 * */
@Data
public class PackingDTO {
	private String chargerCode;
	private String chargerName;
	private String boxSize;
	private Integer boxQy;
	private String deliveryCode;
	private String storageName; //기사 배정을 못만들것 같아서 배차 정보를 여기서 등록
}
