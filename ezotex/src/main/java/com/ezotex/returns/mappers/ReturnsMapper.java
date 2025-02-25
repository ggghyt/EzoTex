package com.ezotex.returns.mappers;

import java.util.List;

import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;
import com.ezotex.returns.dto.ReturnsDTO;
import com.ezotex.returns.dto.ReturnsProductDTO;

public interface ReturnsMapper {
	// 납품 내역 조회
	List<DeliveryReturnsDTO> getDeliveryList();
	
	// 납품 상세 조회
	List<DeliveryDetailsReturnsDTO> getDeliProduct(String deliveryCode);
	
	// 반품 헤더 등록
	int insertReturn(ReturnsDTO returnData);
	
	// 반품 제품 등록
	int insertProductReturn(List<ReturnsProductDTO> returnProductData);
	
}
