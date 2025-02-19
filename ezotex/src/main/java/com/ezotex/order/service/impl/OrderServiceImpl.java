package com.ezotex.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public List<OrderDTO> getProductList() {
		return mapper.getProductList();
	}

	@Override
	public List<OrderDTO> getCompanyList() {
		return mapper.getCompanyList();
	}

	@Override
	public List<OrderDTO> getProductOption(String productCode) {
		return mapper.getProductOption(productCode);
	}
	
	// 주문 등록
	@Override
	public boolean insertOrder(List<OrderDTO> order) {
		order.forEach(data -> {
			System.out.println(data);
			mapper.insertOrder(data);
		} );
		return true;
	}
	
	// 제품 등록
	@Transactional
	@Override
	public boolean insertProductOrder(List<OrderDTO> product) {
		product.forEach(data -> {
			System.out.println("확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인");
			System.out.println(data);
			mapper.insertProductOrder(data);
		});
		return true;
	}


	@Override
	public String getOrderCode() {
		return mapper.getOrderCode();
	}

}
