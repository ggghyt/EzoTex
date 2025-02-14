package com.ezotex.delivery.service;

import java.util.List;

import com.ezotex.delivery.dto.ProductDeliveryDTO;
import com.ezotex.delivery.dto.SearchDTO;

public interface DeliveryService {
	public List<ProductDeliveryDTO> getList(SearchDTO searchDTO);
	
	public int getCount();
	
}
