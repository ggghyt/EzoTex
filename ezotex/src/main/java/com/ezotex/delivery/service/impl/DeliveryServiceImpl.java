package com.ezotex.delivery.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezotex.delivery.dto.ProductDeliveryDTO;
import com.ezotex.delivery.mappers.DeliveryMapper;
import com.ezotex.delivery.service.DeliveryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
	
	private final DeliveryMapper deliveryMapper;
	
	@Override
	public List<ProductDeliveryDTO> getList() {
		return deliveryMapper.findAll();
	}
	
}
