package com.ezotex.delivery.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.delivery.dto.DriverDeliveryDTO;
import com.ezotex.delivery.dto.DriverDeliverySearchDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;

public interface DriverService {
	
	//배송기사 배송지 조회
	public List<DriverDeliveryDTO> deliveryList(DriverDeliverySearchDTO searchDTO);
	
	//배송기사 배송지 목록 조회 카운트
	public int getDeliveryListCount(DriverDeliverySearchDTO searchDTO);
	
	//배송완료
	public String insertDeliver(DriverDeliveryDTO info);
	
	public int updateDeliveryState(String deliveryCode);
	
	//상자 사이즈별 개수
	public List<DriverDeliveryDTO> boxList(String deliveryCode);
	//updateDeliveryState
}
