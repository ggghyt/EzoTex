package com.ezotex.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.mappers.InventoryMapper;
import com.ezotex.store.service.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryMapper mapper;
	
	// 테스트
	@Override
	public List<InventoryDTO> list() {
		return mapper.list();
	}

	// 입고 예정 리스트
	@Override
	public List<StoreDeliveryDTO> DeliveryList() {
		return mapper.DeliveryList();
	}

	// 납품리스트 기반 입고 제품 상세 조회
	@Override
	public List<StoreDeliveryDetailsDTO> findByDeliveryCode(String DeliveryCode) {
		return mapper.findByDeliveryCode(DeliveryCode);
	}

	// 제품코드 기반 옵션 리스트
	@Override
	public Map<String, Object> findByProductCode(String productCode) {
		
		Map<String, Object> map = new HashMap<>();
		
		List<StoreDeliveryDetailsDTO> list = mapper.findBySize(productCode);
		
		map.put("optionList", list);
		map.put("qyList", mapper.findByProductCode(productCode, list));
		
		return map;
	}

	@Override
	public boolean InsertProduct(List<SizeDTO> list) {
		System.out.println(list);
		list.forEach(data -> mapper.InsertProduct(data));
		return true;
	}

	
	
}
