package com.ezotex.delivery.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
import com.ezotex.delivery.dto.OrderProductDeliveryDTO;

public interface SupplyDeliveryMapper {
	//검색 결과 리스트
	public List<OrderProductDeliveryDTO> findAll(@Param("searchDto") DeliveryRegistSearchDTO searchDto);
	
	//검색 결과 총 레코드 수
	public int getCount(@Param("searchDto") DeliveryRegistSearchDTO searchDto);
	
	//발주 상세내용
	public List<OrderInsertDTO> orderInfo(@Param("orderCode")String orderCode);
	
	//주소 가져오기
	public String getAddress(@Param("companyCode")String companyCode);
}
