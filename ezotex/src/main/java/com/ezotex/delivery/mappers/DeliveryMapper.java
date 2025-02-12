package com.ezotex.delivery.mappers;

import java.util.List;

import com.ezotex.delivery.dto.ProductDeliveryDTO;

public interface DeliveryMapper {
	
	List<ProductDeliveryDTO> findAll();
}
