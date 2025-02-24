package com.ezotex.delivery.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;

public interface DeliveryService {
	public List<OrderProductDeliveryDTO> getList(DeliveryRegistSearchDTO searchDTO);
	
	public int getCount(DeliveryRegistSearchDTO searchDTO);
	
	public List<OrderInfoDTO> getOrderInfo(String prdOrderCode);
	
	// 제품코드 기반 옵션 리스트
	public Map<String, Object> findByProductCode(String productCode, String orderCode);
	
	//출고 등록
	public String insertDelivery(List<OrderInsertDTO> orderInfoList);
	
	//출고 조회
	public List<OrderProductDeliveryDTO> deliveryList(DeliveryRegistSearchDTO searchDTO);
	
	//출고조회 카운트
	public int deliveryListCnt(@Param("cri") DeliveryRegistSearchDTO searchDTO);
	
	//출고 조회 단건
	public Map<String, Object> deliveryInfo(String deliveryCode);
	
	//출고건 제품 출고 내역
	public Map<String, Object> deliveryProductDetails(String deliveryCode,
													  String productCode);
}
