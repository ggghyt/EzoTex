package com.ezotex.order.mappers;

import java.util.List;

import com.ezotex.order.dto.OrderDTO;

public interface OrderMapper {
		// 주문 조회
		List<OrderDTO> getOrderList();
		// 주문 등록
		int insertOrder(OrderDTO order);
		// 제품 코드 조회
		List<OrderDTO> getProductList();
}