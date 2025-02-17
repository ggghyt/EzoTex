package com.ezotex.delivery.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.DeliveryProductInfo;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.mappers.DeliveryMapper;
import com.ezotex.delivery.service.DeliveryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
	
	private final DeliveryMapper mapper;

	@Override
	public List<OrderProductDeliveryDTO> getList(DeliveryRegistSearchDTO searchDTO) {
		return mapper.findAll(searchDTO);
	}

	@Override
	public int getCount(DeliveryRegistSearchDTO searchDTO) {
		return mapper.getCount(searchDTO);
	}

	@Override
	public List<OrderInfoDTO> getOrderInfo(String prdOrderCode) {
		return mapper.getOrderInfo(prdOrderCode);
	}

	@Override
	public Map<String, Object> findByProductCode(String productCode, String orderCode) {
		Map<String, Object> map = new HashMap<>();
		
		List<DeliveryProductInfo> list = mapper.findBySize(productCode);
		
		map.put("optionList", list);
		map.put("qyList", mapper.sizeFindByProductCode(productCode, list, orderCode));
		
		return map;
	}



	
}
