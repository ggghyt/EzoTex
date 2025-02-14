package com.ezotex.delivery.dto;



import java.util.Date;

import lombok.Data;

@Data
public class DeliveryOrderListSearchDTO {
	
	int start;
	int end;
	
	//주문코드
	String orderCode;
	//납품코드
	String deliveryCode;
	//주문담당자
	String orderCharger;
	//납품담당자
	String deliveryCharger;
	//주문일시작
	String orderDateStart;
	//주문일끝
	String orderDateEnd;
	//납기일시작
	String dedtStart;
	//납기일끝
	String dedtEnd;
	//금액합계
	Integer totalAmountStart;
	//금액합계
	Integer totalAmountEnd;
	//업체코드
	String companyCode;
	//업체명
	String companyName;
	//상태
	String status;
}
