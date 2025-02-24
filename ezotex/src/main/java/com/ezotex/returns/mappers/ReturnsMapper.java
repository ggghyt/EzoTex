package com.ezotex.returns.mappers;

import java.util.List;

import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;

public interface ReturnsMapper {
	// 출고 내역 조회
	List<DeliveryReturnsDTO> getDeliveryList();

	List<DeliveryDetailsReturnsDTO> getDeliProduct(String deliveryCode);
	
}
