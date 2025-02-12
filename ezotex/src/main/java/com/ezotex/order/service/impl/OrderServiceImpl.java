package com.ezotex.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.order.dto.OrderDTO;
import com.ezotex.order.mappers.OrderMapper;
import com.ezotex.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderMapper mapper;
	
	@Override
	public List<OrderDTO> getOrderList() {
		return mapper.getOrderList();
	}

	@Override
	public boolean insertOrder(OrderDTO order) {
		return mapper.insertOrder(order) > 0;
	}

	@Override
	public List<OrderDTO> getProductList() {
		return mapper.getProductList();
	}

}
