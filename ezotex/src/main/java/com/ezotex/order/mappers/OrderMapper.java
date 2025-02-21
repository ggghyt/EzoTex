package com.ezotex.order.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezotex.order.dto.OrderDTO;

public interface OrderMapper {
		// 주문 조회
		List<OrderDTO> getOrderList();
		// 제품 코드 조회
		List<OrderDTO> getProductList();
		// 업체 조회
		List<OrderDTO> getCompanyList();
		// 제품 옵션 조회
		List<OrderDTO> getProductOption(String productCode);
		// 주문 등록
		int insertOrder(OrderDTO order);
		// 제품 등록
		int insertProductOrder(OrderDTO productOrderList);
		
		// 주문 코드
		String getOrderCode();
		
		// 주문
		List<OrderDTO> getOrderProduct(String productOrderCode);
}