package com.ezotex.delivery.service;

import java.util.List;

import com.ezotex.delivery.dto.ProductDeliveryDTO;
import com.ezotex.delivery.dto.DeliveryOrderListSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;

public interface DeliveryService {
	public List<ProductDeliveryDTO> getList(DeliveryOrderListSearchDTO searchDTO);
	
	public int getCount(DeliveryOrderListSearchDTO searchDTO);
	
	public List<OrderInfoDTO> getOrderInfo(String prdOrderCode);
	
}
