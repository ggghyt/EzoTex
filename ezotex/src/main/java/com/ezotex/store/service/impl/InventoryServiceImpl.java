package com.ezotex.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.mappers.InventoryMapper;
import com.ezotex.store.mappers.StoreMapper;
import com.ezotex.store.service.InventoryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryMapper mapper;
	private final StoreMapper smapper;
	
	 @Autowired
	 private HttpSession session;  // HttpSession을 클래스 멤버로 주입받음

	// 제품별 총 수량
	@Override
	public List<InventoryDTO> productList() {
		return mapper.productList();
	}

	// 제품별 옵션 리스트
	@Override
	public Map<String, Object> productInfoList(String productCode) {
		
		Map<String, Object> map = new HashMap<>();
		
		List<StoreDeliveryDetailsDTO> list = smapper.findBySize(productCode);
		map.put("optionList", list);
		map.put("pivotList", mapper.productInfoList(productCode, list));
		
		return map;
	}

	

}
