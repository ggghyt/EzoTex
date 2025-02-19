package com.ezotex.delivery.dto;

import lombok.Data;


/*
 * 제품 출고 등록시 사용하는 DTO
 * 
 * */
@Data
public class OrderInsertDTO {
	
	/* 주문 상태
	 * OR01 대기
	   OR02 입고
	   OR03 출고
	   OR04 주문완료
	   OR05 주문취소
	   OR06 부분출고
	 */
	
	//제품 주문정보
	private String deliveryCode; 	//출고코드
	private String orderStatus;		//주문 상태	--> OR06이면 부분출고
	private String companySe;		//회사 구분(공급업체에서 사용하는지, 제조업체에서 사용하는지)
	private String productOrderCode;//제품 주문 코드(제조업체 -> 구매업체)
	private String companyCode;		//받는 업체 코드
	private String companyName;		//업체명
	private String orderDate;		//주문일
	private String dedt;			//납기일
	private String storageName;		//출고 창고, 또는 배송 출발지
	private String dedtAddress;		//배송 받는 곳 주소
	private String deliveryStatus;	//배송 상태
	private String remark;			//비고
	private String chargerCode;	//담당자 코드
	private String chargerName;		//담당자 명
	private String rgsde;			//등록일
	private String updde;			//수정일
	private Integer time;			//회차(분할배송)
	private Integer amount;			//수량
	
	//제품정보
	private String productCode;		//제품 코드
	private String productColor;	//제품 색상
	private String productSize;		//제품 사이즈
	private Integer reqQy;	    	//요청수량
	private Integer deliveryQy; 	//출고수량
	private String productSe; 		//제품 구분
	private String productLot;		//제품 로트
	private Integer lotQy;			//로트 수량


}
