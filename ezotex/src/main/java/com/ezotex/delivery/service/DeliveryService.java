package com.ezotex.delivery.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.PackingDTO;
import com.ezotex.delivery.dto.DeliveryProductInfo;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.DeliveryScheduleDTO;
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
	public int deliveryListCnt(DeliveryRegistSearchDTO searchDTO);
	
	//출고 조회 단건
	public Map<String, Object> deliveryInfo(String deliveryCode);
	
	//출고건 제품 출고 내역
	public Map<String, Object> deliveryProductDetails(String deliveryCode,
													  String productCode);
	
	//포장 조회
	public List<OrderProductDeliveryDTO> packingList(DeliveryRegistSearchDTO searchDTO);
	
	//포장 조회 카운트
	public int packingListCnt(DeliveryRegistSearchDTO searchDTO);
	
	// 출고 제품 조회
	public List<DeliveryProductInfo> deliveryProductListWithLot(String deliveryCode);
	
	//납품 로트
    public List<OrderInsertDTO> deliveryLot(@Param("info")OrderInsertDTO info);
    
    //포장에 필요한 정보들 가져오기
    public Map<String, Object> packingSTDInfo();
    
    //포장 등록
    public String insertPackingInfo(List<PackingDTO> info);
    
    //출고 스케줄 등록
    public String insertDivySchedule(DeliveryScheduleDTO info);
    
}
