package com.ezotex.delivery.service;

import java.util.List;

import com.ezotex.delivery.dto.DriverDeliveryDTO;
import com.ezotex.delivery.dto.DriverDeliverySearchDTO;

public interface DriverService {
	
	//배송기사 배송지 조회
	public List<DriverDeliveryDTO> deliveryList(DriverDeliverySearchDTO searchDTO);
	
	//배송기사 배송지 목록 조회 카운트
	public int getDeliveryListCount(DriverDeliverySearchDTO searchDTO);
}
