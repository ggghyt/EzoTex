package com.ezotex.delivery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
import com.ezotex.delivery.mappers.DeliveryMapper;
import com.ezotex.delivery.mappers.SupplyDeliveryMapper;
import com.ezotex.delivery.service.SupplyDeliveryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplyDeliveryServiceImpl implements SupplyDeliveryService {
	
	private final SupplyDeliveryMapper mapper;
	
	 @Autowired
	 private HttpSession session;
	 //String name = (String) session.getAttribute("name");

	@Override
	public List<OrderProductDeliveryDTO> getList(DeliveryRegistSearchDTO searchDTO) {
		return mapper.findAll(searchDTO);
	}

	@Override
	public int getCount(DeliveryRegistSearchDTO searchDTO) {
		return mapper.getCount(searchDTO);
	}

	@Override
	public List<OrderInsertDTO> orderInfo(String orderCode) {
		return mapper.orderInfo(orderCode);
	}

	@Override
	public OrderInfoDTO getAddress(String CompanyCode) {
		OrderInfoDTO oInfo = new OrderInfoDTO();
		oInfo.setAddress(mapper.getAddress(CompanyCode));
		return oInfo;
	}
	
	





	
}
