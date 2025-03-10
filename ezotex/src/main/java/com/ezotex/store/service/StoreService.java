package com.ezotex.store.service;

import java.util.List;
import java.util.Map;

import com.ezotex.store.dto.DeliverySearchDTO;
import com.ezotex.store.dto.DomListDTO;
import com.ezotex.store.dto.ProductInfoListDTO;
import com.ezotex.store.dto.ProductInfoSearchDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.dto.StoreReturnDTO;

public interface StoreService {

	
	// 제품 목록 리스트
	public List<ProductInfoListDTO> productInfoList(DeliverySearchDTO searchDTO);
	
	// 제품 목록 total
	public int productInfoTotal(DeliverySearchDTO searchDTO);
	
	// 제품 컬러
	public List<ProductInfoListDTO> productColor(String productColor);
	
	// 제품 사이즈
	public List<ProductInfoListDTO> productSize(String productCode ,String productColor);
	
	// 공급계획 리스트
	public List<DomListDTO> domList(DeliverySearchDTO searchDTO);
	
	// 공급계획 리스트 카운터
	public int domListCount(DeliverySearchDTO searchDTO);
	
	
	/**
	 * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 * */
	
	// 페이지 총 수(제품)
	public int getCount(DeliverySearchDTO searchDTO);
	
	// 페이지 총 수(자재)
	public int getMtCount(DeliverySearchDTO searchDTO);
	
	// 납품리스트별 총 제품 수량
	//public StoreDeliveryDTO deliveryQy();
	
	// 입고 예정 리스트(제품)
	public List<StoreReturnDTO> DeliveryList(DeliverySearchDTO searchDTO);
	
	// 입고 예정 리스트(자재)
	public List<StoreDeliveryDTO> MtDeliveryList(DeliverySearchDTO searchDTO);
	
	// 반품리스트 기반 입고 반품 상세 조회
	public List<StoreReturnDTO> findByDeliveryCode(String returnCode);
	
	// 납품리스트 기반 입고 자재 상세 조회
	public List<StoreDeliveryDetailsDTO> findByMtDeliveryCode(String DeliveryCode);
	
	// 제품코드 기반 옵션 리스트
	public Map<String, Object> findByProductCode(String productCode, String returnCode);
	
	// 제품 입고 등록
	public boolean InsertProduct(List<SizeDTO> list); 
	
	// 반품 제품 입고 등록
	public boolean returnInsertProduct(List<SizeDTO> list); 
	
	// 자재 입고 등록
	public boolean MtInsertProduct(List<StoreDeliveryDetailsDTO> list);

	
	/**
	 * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 * */
	
}
