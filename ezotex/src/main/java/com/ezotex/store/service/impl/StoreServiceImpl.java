package com.ezotex.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.store.dto.DeliverySearchDTO;
import com.ezotex.store.dto.DomListDTO;
import com.ezotex.store.dto.ProductInfoListDTO;
import com.ezotex.store.dto.ProductInfoSearchDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.dto.StoreReturnDTO;
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
	 
	// 제품 목록 리스트
	@Override
	public List<ProductInfoListDTO> productInfoList(ProductInfoSearchDTO searchDTO) {
		return mapper.productInfoList(searchDTO);
	}
	 
	// 제품 목록 total 
	@Override
	public int productInfoTotal(ProductInfoSearchDTO searchDTO) {
		return mapper.productInfoTotal(searchDTO);
	}
	
	// 제품별 색상
	@Override
	public List<ProductInfoListDTO> productColor(String productColor) {
		return mapper.productColor(productColor);
	}
	 
	// 제품 사이즈
	@Override
	public List<ProductInfoListDTO> productSize(String productCode, String productColor) {
		return mapper.productSize(productCode, productColor);
	}
	 
	 
	// 공급계획 리스트
	@Override
	public List<DomListDTO> domList(DeliverySearchDTO searchDTO) {
		return mapper.domList(searchDTO);
	}
	
	// 공급계획 카운터
	@Override
	public int domListCount(DeliverySearchDTO searchDTO) {
		return mapper.domListCount(searchDTO);
	}
	 
	
	 /**
	  * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 **/
	 
	 
	// 페이지 총 수(제품)
	@Override
	public int getCount(DeliverySearchDTO searchDTO) {
		return mapper.getCount(searchDTO);
	}
	
	// 페이지 총 수(자재)
	@Override
	public int getMtCount(DeliverySearchDTO searchDTO) {
		return mapper.getMtCount(searchDTO);
	}
	
	// 납품리스트별 총 제품 수량
//	@Override
//	public StoreDeliveryDTO deliveryQy() {
//		return mapper.deliveryQy();
//	}

	// 입고 예정 리스트(제품)
	@Override
	public List<StoreReturnDTO> DeliveryList(DeliverySearchDTO searchDTO) {
		//mapper.deliveryQy();
		return mapper.DeliveryList(searchDTO);
	}
	
	// 입고 예정 리스트(자재)
	@Override
	public List<StoreDeliveryDTO> MtDeliveryList(DeliverySearchDTO searchDTO) {
		return mapper.MtDeliveryList(searchDTO);
	}

	// 반품리스트 기반 입고 반품 상세 조회
	@Override
	public List<StoreReturnDTO> findByDeliveryCode(String returnCode) {
		return mapper.findByDeliveryCode(returnCode);
	}
	
	// 납품리스트 기반 입고 자재 상세 조회
	@Override
	public List<StoreDeliveryDetailsDTO> findByMtDeliveryCode(String DeliveryCode) {
		return mapper.findByMtDeliveryCode(DeliveryCode);
	}
	

	// 제품코드 기반 옵션 리스트
	@Override
	public Map<String, Object> findByProductCode(String productCode, String returnCode) {
		
		Map<String, Object> map = new HashMap<>();
		
		List<StoreDeliveryDetailsDTO> list = mapper.findBySizeInventory(productCode);
		map.put("optionList", list);
		map.put("qyList", mapper.findByProductCode(productCode, list, returnCode));
		
		return map;
	}

	
	// 제품 옵션별 등록 및 업데이트
	@Override
	public boolean InsertProduct(List<SizeDTO> list) {
		
		String name = (String) session.getAttribute("name");
		System.out.println(list);
		list.forEach(data -> {
			if(data.getProductQy() > 0) {
				//mapper.InsertProduct(data, name);
			}
	    });
		return true;
	}

	
	// 자재 등록 및 업데이트
	@Override
	public boolean MtInsertProduct(List<StoreDeliveryDetailsDTO> list) {
		
		String name = (String) session.getAttribute("name");
		
		list.forEach(data -> {
			System.out.println(data);
			mapper.MtInsertProduct(data, name);
		});
		
		return false;
	}

	

	/**
	 * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 * */	

	
}
