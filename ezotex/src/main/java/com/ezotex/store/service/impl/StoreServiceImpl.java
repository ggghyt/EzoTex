package com.ezotex.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		System.out.println("서비스 : " + DeliveryCode);
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

	        String[] sizes = {data.getSizeFREE(),data.getSizeXS(),data.getSizeS(), data.getSizeM(), data.getSizeL(), data.getSizeXL()};
	        String[] sizeCode = {"SI01","SI02","SI03","SI04","SI05","SI06","SI07",};
	        
	        // productColor와 productCode는 동일하므로, 각각 한번만 출력
	        String color = data.getProductColor();
	        String productCode = data.getProductCode();
	        String deliveryCode = data.getDeliveryCode();
	        
	        if (color != null) {
	            for (int i = 0; i < sizes.length; i++) {
	                if (sizes[i] != null) {
	                	SizeDTO sizeDto = new SizeDTO(sizeCode[i], sizes[i], color, productCode, name);
	                	mapper.InsertProduct(sizeDto);
	                	StoreDeliveryDetailsDTO deliveryDetailDto = new StoreDeliveryDetailsDTO(sizeCode[i], sizes[i], color, productCode, deliveryCode);
	                	System.out.println("서비스 : " + deliveryDetailDto);
	                	mapper.UpdateDeliveryDtails(deliveryDetailDto);
	                }
	            }
	        }
	    });
		return true;
	}

	
	
}
