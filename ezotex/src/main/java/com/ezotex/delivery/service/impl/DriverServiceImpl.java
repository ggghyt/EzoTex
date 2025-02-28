package com.ezotex.delivery.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezotex.delivery.dto.DriverDeliveryDTO;
import com.ezotex.delivery.dto.DriverDeliverySearchDTO;
import com.ezotex.delivery.mappers.DriverMapper;
import com.ezotex.delivery.service.DriverService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService{
	
	private final DriverMapper mapper;
	
	//배송기사 배송지 조회
	@Override
	public List<DriverDeliveryDTO> deliveryList(DriverDeliverySearchDTO searchDTO) {
		return mapper.deliveryList(searchDTO);
	}

	@Override
	public int getDeliveryListCount(DriverDeliverySearchDTO searchDTO) {
		return mapper.getDeliveryListCount(searchDTO);
	}
	
	
}
