package com.ezotex.delivery.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.delivery.dto.DriverDeliveryDTO;
import com.ezotex.delivery.dto.DriverDeliverySearchDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
import com.ezotex.delivery.mappers.DriverMapper;
import com.ezotex.delivery.service.DriverService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService{
	
	private final DriverMapper mapper;
	
	@Autowired
	 private HttpSession session;
	 //String name = (String) session.getAttribute("name");
	
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
	
	//배송상태 업데이트
	@Override
	public int updateDeliveryState(String deliveryCode) {
		DriverDeliveryDTO info = new DriverDeliveryDTO();
		
		String name = (String) session.getAttribute("name");
		String code = (String) session.getAttribute("code");
		
		info.setChargerCode(code);
		info.setChargerName(name);
		info.setDeliveryCode(deliveryCode);
		
		mapper.updateDriverInfo(info);
		return mapper.updateDeliveryState(deliveryCode);
	}


	
	
}
