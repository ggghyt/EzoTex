package com.ezotex.delivery.dto;

import lombok.Data;

/*
 * 주문정보 (제조업체, 공급업체 사용)
 * */
@Data
public class OrderInfoDTO {
	private String charger;
	private Integer deliveryQy;
	private Integer reqQy;
	private Integer amount;
	private String address;
	private String productCode;
	private String productName;
	private String productColor;
	private String remark;
	private String productOrderCode;
}
