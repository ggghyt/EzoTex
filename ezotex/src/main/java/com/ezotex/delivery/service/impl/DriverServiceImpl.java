package com.ezotex.delivery.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.delivery.dto.DriverDeliveryDTO;
import com.ezotex.delivery.dto.DriverDeliverySearchDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
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

	@Override
	@Transactional
	public String insertDeliver(DriverDeliveryDTO info) {
		//입력
		mapper.insertDeliveryInfo(info);
		
		//
		mapper.updateDeliveryState(info.getDeliveryCode());
		
		return "success";
	}


	
	
}
