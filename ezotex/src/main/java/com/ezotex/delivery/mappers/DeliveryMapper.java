package com.ezotex.delivery.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.DeliveryProductInfo;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;

public interface DeliveryMapper {
	
	//주문 등록건 전체 조회
	public List<OrderProductDeliveryDTO> findAll(@Param("cri") DeliveryRegistSearchDTO cri);
	
	//주문 등록건 전체 개수(페이징)
	public int getCount(@Param("cri") DeliveryRegistSearchDTO searchDTO);
	
	//주문 등록건 단건조회
	public List<OrderInfoDTO> getOrderInfo(String prdOrderCode);
	
	// 제품별 사이즈 리스트
    public List<DeliveryProductInfo> findBySize(String productCode);
	
	// 제품코드 기반 옵션 리스트
	public List<DeliveryProductInfo> sizeFindByProductCode(@Param("productCode")String productCode, 
															@Param("list")List<DeliveryProductInfo> list,
															@Param("orderCode")String orderCode);
}
