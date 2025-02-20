package com.ezotex.order.service.impl;

import java.util.List;
import java.util.Map;

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
	public OrderDTO insertOrder(OrderDTO order) {
			System.out.println(order);
			mapper.insertOrder(order);
		return order;
	}

	// 제품 등록
	/*
	 * @Transactional
	 * 
	 * @Override public boolean insertProductOrder(List<OrderDTO> product) {
	 * System.out.println("check check check check "+product); // 디버깅을 위해 제품 정보를 출력
	 * // 제품 리스트를 반복문으로 처리 product.forEach(data -> { // 각 제품을 데이터베이스에 등록하는 로직
	 * mapper.insertProductOrder(data); // 데이터베이스에 제품 등록 }); return true; }
	 */
	@Transactional
	@Override
	public boolean insertProductOrder(Map<String, Object> product) {

		String productOrderCode = (String) product.get("productOrderCode");

		List<OrderDTO> odto = (List<OrderDTO>) product.get("option");
		System.out.println("sdsd"+productOrderCode);
		System.out.println("dsds"+odto);
		odto.forEach(data -> {
			data.setProductOrderCode(productOrderCode);
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
