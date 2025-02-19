package com.ezotex.order.service;

import java.util.List;

import com.ezotex.order.dto.OrderDTO;

public interface OrderService {
	List<OrderDTO> getOrderList();
	List<OrderDTO> getProductList();
	List<OrderDTO> getCompanyList();
	List<OrderDTO> getProductOption(String productCode);
	boolean insertOrder(List<OrderDTO> order);
	boolean insertProductOrder(List<OrderDTO> productOrderList);
	String getOrderCode();
}
