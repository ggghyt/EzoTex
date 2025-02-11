package com.ezotex.delivery.mappers;

import java.util.List;

import com.ezotex.delivery.dto.RegistDeliveryDTO;

public interface DeliveryMapper {
	
	List<RegistDeliveryDTO> findAll();
}
