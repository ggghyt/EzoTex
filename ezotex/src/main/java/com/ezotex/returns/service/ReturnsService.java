package com.ezotex.returns.service;

import java.util.List;

import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;

public interface ReturnsService {
	List<DeliveryReturnsDTO> getDeliveryList();
	
	List<DeliveryDetailsReturnsDTO> getDeliProduct(String deliveryCode);
}
