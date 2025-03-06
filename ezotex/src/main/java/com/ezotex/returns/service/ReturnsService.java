package com.ezotex.returns.service;

import java.util.List;
import java.util.Map;

import com.ezotex.returns.dto.ChangeOrderDTO;
import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;
import com.ezotex.returns.dto.ReturnsDTO;
import com.ezotex.returns.dto.ReturnsProductDTO;
import com.ezotex.returns.dto.SalesDTO;
import com.ezotex.returns.dto.changeDTO;
import com.ezotex.returns.dto.showDTO;

public interface ReturnsService {
	// 납품 내역 조회
	List<DeliveryReturnsDTO> getDeliveryList();
	
	// 납품 상세 조회
	List<DeliveryDetailsReturnsDTO> getDeliProduct(String deliveryCode);
	
	// 반품 헤더 등록
	ReturnsDTO insertReturn(ReturnsDTO returnData);
	
	// 반품 제품 등록
	boolean insertProductReturn(List<ReturnsProductDTO> returnProductData);
	
	// 교환 조회
	List<changeDTO> getChangeList();
	List<changeDTO> getChangeProductList(String returnCode);
	// 반품 전체 조회
	List<ReturnsDTO> getReturnList();
	List<ReturnsProductDTO> getReturnProductList();
	
	// 교환 주문 등록
	ChangeOrderDTO insertOrder(ChangeOrderDTO order);
	// 교환 주문 제품 등록
	boolean insertProductOrder(Map<String, Object> product);
	// 교환 제품 show 변경
	boolean showChange(List<showDTO> newReturnData);
	
	// 반품 제품 조회
	List<ReturnsProductDTO> selectReturnProductList(String returnCode);
	
	// 일별 제품별 손실액
	List<ReturnsProductDTO> getTotalReturnProduct();
	
	// 매출액 조회
	List<SalesDTO> getSalesList();
	
	// 주문코드에 대한 매출액과 손실액 조회
	List<SalesDTO> getSalesAmount();
}
