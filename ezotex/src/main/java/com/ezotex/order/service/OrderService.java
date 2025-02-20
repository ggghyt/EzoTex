package com.ezotex.order.service;

import java.util.List;
import java.util.Map;

import com.ezotex.order.dto.OrderDTO;

public interface OrderService {
	List<OrderDTO> getOrderList();
	List<OrderDTO> getProductList();
	List<OrderDTO> getCompanyList();
	List<OrderDTO> getProductOption(String productCode);
	OrderDTO insertOrder(OrderDTO order);
	boolean insertProductOrder(Map<String, Object> product);
	String getOrderCode();
}
