package com.ezotex.delivery.service;

import java.util.List;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;

public interface SupplyDeliveryService {
	//주문 목록
	public List<OrderProductDeliveryDTO> getList(DeliveryRegistSearchDTO searchDTO);
	
	//주문목록 총 개수
	public int getCount(DeliveryRegistSearchDTO searchDTO);
	
	//주문 단건
	public List<OrderInsertDTO> orderInfo(String orderCode);
	
	//주소 구하기
	public OrderInfoDTO getAddress(String CompanyCode);
	
	//출고 등록
	public String insertDelivery(List<OrderInsertDTO> orderInfoList);
}
