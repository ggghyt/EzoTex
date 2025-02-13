package com.ezotex.delivery.service;

import java.util.List;

import com.ezotex.delivery.dto.ProductDeliveryDTO;
import com.ezotex.delivery.dto.DeliveryOrderListSearchDTO;

public interface DeliveryService {
	public List<ProductDeliveryDTO> getList(DeliveryOrderListSearchDTO searchDTO);
	
	public int getCount();
	
}
