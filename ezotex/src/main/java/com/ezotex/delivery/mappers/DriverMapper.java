package com.ezotex.delivery.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezotex.delivery.dto.DriverDeliveryDTO;
import com.ezotex.delivery.dto.DriverDeliverySearchDTO;

public interface DriverMapper {
	
	//배송기사 배송지 목록 조회
	public List<DriverDeliveryDTO> deliveryList(@Param("search")DriverDeliverySearchDTO searchDTO);
	
	//배송기사 배송지 목록 조회 카운트
	public int getDeliveryListCount(@Param("search")DriverDeliverySearchDTO searchDTO);
	
	//배송정보 인서트
	public int insertDeliveryInfo(@Param("info")DriverDeliveryDTO info);
	
	//출고 상태 업데이트
	public int updateDeliveryState(@Param("deliveryCode")String deliveryCode);
	
	//배송 담당자 이름 업데이트
	public int updateDriverInfo(@Param("info")DriverDeliveryDTO info);
}
