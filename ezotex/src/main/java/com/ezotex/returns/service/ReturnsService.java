package com.ezotex.returns.service;

import java.util.List;

import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;
import com.ezotex.returns.dto.ReturnsDTO;
import com.ezotex.returns.dto.ReturnsProductDTO;
import com.ezotex.returns.dto.changeDTO;

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
}
