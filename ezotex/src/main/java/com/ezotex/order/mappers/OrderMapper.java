package com.ezotex.order.mappers;

import java.util.List;

import com.ezotex.order.dto.OrderDTO;

public interface OrderMapper {
		// 조회
		List<OrderDTO> getOrderList();
		// 등록
		int insertOrder(OrderDTO order);
}