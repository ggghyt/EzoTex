package com.ezotex.delivery.service;

import java.util.List;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;

public interface SupplyDeliveryService {
	public List<OrderProductDeliveryDTO> getList(DeliveryRegistSearchDTO searchDTO);
	
	public int getCount(DeliveryRegistSearchDTO searchDTO);
	
}
