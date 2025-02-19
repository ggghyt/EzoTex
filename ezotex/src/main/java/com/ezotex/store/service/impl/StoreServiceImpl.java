package com.ezotex.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.mappers.StoreMapper;
import com.ezotex.store.service.StoreService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	private final StoreMapper mapper;
	
	 @Autowired
	 private HttpSession session;  // HttpSession을 클래스 멤버로 주입받음
	
	// 페이지 총 수
	@Override
	public int getCount() {
		return mapper.getCount();
	}

	// 입고 예정 리스트
	@Override
	public List<StoreDeliveryDTO> DeliveryList(PagingDTO paging) {
		mapper.deliveryQy();
		return mapper.DeliveryList(paging);
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
		
		String name = (String) session.getAttribute("name");
        
		list.forEach(data -> {
	        mapper.InsertProduct(data, name);
	        mapper.UpdateDeliveryDtails(data);
	    });
		return true;
	}

	

	
	
}
