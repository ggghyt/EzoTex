package com.ezotex.delivery.dto;


import lombok.Data;

/*
 * 납품 관리 페이지 주문건 검색 DTO(제조업체, 공급업체 둘다 사용)
 * */
@Data
public class DeliveryRegistSearchDTO {
	
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
	//주문 상태
	String orderStatus;
	//제품명
	String productName;
	//제품코드
	String productCode;
	
}
