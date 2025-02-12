package com.ezotex.order.service;

import java.util.List;

import com.ezotex.order.dto.OrderDTO;

public interface OrderService {
	List<OrderDTO> getOrderList();
	boolean insertOrder(OrderDTO order);
	List<OrderDTO> getProductList();
}
