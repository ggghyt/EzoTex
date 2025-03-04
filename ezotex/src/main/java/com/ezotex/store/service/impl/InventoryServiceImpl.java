package com.ezotex.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.store.dto.DeliverySearchDTO;
import com.ezotex.store.dto.ErrorProductDTO;
import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.dto.storageInfoDTO;
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
	public List<InventoryDTO> productList(DeliverySearchDTO searchDTO) {
		return mapper.productList(searchDTO);
	}

	// 제품목록 카운터
	@Override
	public int productListCount(DeliverySearchDTO searchDTO) {
		return mapper.productListCount(searchDTO);
	}
	
	// 제품별 옵션 리스트
	@Override
	public Map<String, Object> productInfoList(String productCode) {
		
		Map<String, Object> map = new HashMap<>();
		
		List<StoreDeliveryDetailsDTO> list = smapper.findBySizeInventory(productCode);
		map.put("optionList", list);
		map.put("pivotList", mapper.productInfoList(productCode, list));
		
		return map;
	}

	// 제품 옵션별 LOT 리스트
	@Override
	public List<InventoryDTO> inventoryList(String productCode, String color, String sizeCode) {
		return mapper.inventoryList(productCode, color, sizeCode);
	}

	// 위치별 재고 상세 리스트(재고조정)
	@Override
	public List<InventoryDTO> location(DeliverySearchDTO searchDTO) {
		return mapper.location(searchDTO);
	}
	
	// 위치별 재고 상세리스트 카운터
	@Override
	public int getCountProduct(DeliverySearchDTO searchDTO) {
		return mapper.getCountProduct(searchDTO);
	}

	// LOT별 불량처리 등록
	@Override
	public boolean InsertErrorProduct(ErrorProductDTO edto) {
		
		String name = (String)session.getAttribute("name");
		mapper.InsertErrorProduct(edto, name);
		mapper.InventoryUpdate(edto);
		return true;
	}

	// 창고위치 리스트
	@Override
	public List<storageInfoDTO> storageInfoList() {
		return mapper.storageInfoList();
	}

	// 재고조정 창고위치 이동
	@Override
	public boolean locationUpdate(List<SizeDTO> sdto) {
		sdto.forEach(data -> {			
			mapper.locationUpdate(data);
		});
		return true;
	}
	
	// 불량 재고 조회
	@Override
	public List<ErrorProductDTO> errorProductList(DeliverySearchDTO searchDTO) {
		return mapper.errorProductList(searchDTO);
	}

	@Override
	public int errorProductCount(DeliverySearchDTO searchDTO) {
		return mapper.errorProductCount(searchDTO);
	}

}
