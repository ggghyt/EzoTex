package com.ezotex.delivery.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezotex.delivery.dto.ProductDeliveryDTO;
import com.ezotex.delivery.dto.SearchDTO;

public interface DeliveryMapper {
	
	//주문 등록건 전체 조회
	public List<ProductDeliveryDTO> findAll(@Param("cri") SearchDTO cri);
	
	//주문 등록건 전체 개수(페이징)
	public int getCount();
}
