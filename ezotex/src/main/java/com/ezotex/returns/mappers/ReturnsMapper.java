package com.ezotex.returns.mappers;

import java.util.List;

import com.ezotex.returns.dto.ChangeOrderDTO;
import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;
import com.ezotex.returns.dto.ReturnsDTO;
import com.ezotex.returns.dto.ReturnsProductDTO;
import com.ezotex.returns.dto.changeDTO;
import com.ezotex.returns.dto.showDTO;

public interface ReturnsMapper {
	// 납품 내역 조회
	List<DeliveryReturnsDTO> getDeliveryList();
	
	// 납품 상세 조회
	List<DeliveryDetailsReturnsDTO> getDeliProduct(String deliveryCode);
	
	// 반품 헤더 등록
	int insertReturn(ReturnsDTO returnData);
	
	// 반품 제품 등록
	int insertProductReturn(ReturnsProductDTO returnProductData);
	
	// 교환 조회
	List<changeDTO> getChangeList();
	List<changeDTO> getChangeProductList(String returnCode);
	
	// 반품 전체 조회
	List<ReturnsDTO> getReturnList();
	List<ReturnsProductDTO> getReturnProductList();
	
	// 교환 주문 등록
	int insertOrder(ChangeOrderDTO order);
	// 교환 주문 제품 등록
	int insertProductOrder(ChangeOrderDTO productOrderList);
	
	// show 변경
	int showChange(showDTO newReturnData);
	
	// 반품 제품 조회
	List<ReturnsProductDTO> selectReturnProductList(String returnCode);
	
	// 일별 제품별 손실액
	List<ReturnsProductDTO> getTotalReturnProduct();
}
