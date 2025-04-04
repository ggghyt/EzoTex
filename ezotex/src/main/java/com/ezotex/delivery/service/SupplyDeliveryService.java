package com.ezotex.delivery.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
    //출고 내역 조회
    public List<OrderProductDeliveryDTO> supplyDeliveryList(@Param("cri")DeliveryRegistSearchDTO searchDto);
    
    //출고 내역 조회 총 레코드 수
    public int supplyDeliveryListCount(@Param("cri")DeliveryRegistSearchDTO searchDto);
    
    //출고 단건조회
    public List<OrderInfoDTO> deliveryInfo(@Param("deliveryCode")String deliveryCode);
}
