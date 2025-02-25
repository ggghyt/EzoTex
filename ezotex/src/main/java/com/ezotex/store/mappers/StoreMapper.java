package com.ezotex.store.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.store.dto.DeliverySearchDTO;
import com.ezotex.store.dto.ProductInfoListDTO;
import com.ezotex.store.dto.ProductInfoSearchDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;

public interface StoreMapper {
	
	// 제품 목록 리스트
	public List<ProductInfoListDTO> productInfoList(ProductInfoSearchDTO searchDTO);
	
	// 제품 목록 total
	public int productInfoTotal(ProductInfoSearchDTO searchDTO);
	
	// 제품 컬러
	public List<ProductInfoListDTO> productColor(String productColor);
	
	
	/**
	 * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 * */
	
	// 페이지 총 수(제품)
	public int getCount(DeliverySearchDTO searchDTO);
	
	// 페이지 총 수(자재)
	public int getMtCount(DeliverySearchDTO searchDTO);
	
	// 입고 예정 리스트(제품)
	public List<StoreDeliveryDTO> DeliveryList(DeliverySearchDTO searchDTO);
	
	// 입고 예정 리스트(자재)
	public List<StoreDeliveryDTO> MtDeliveryList(DeliverySearchDTO searchDTO);
	
	// 납품리스트별 총 제품 수량
	//public StoreDeliveryDTO deliveryQy();
	
	// 납품리스트 기반 입고 제품 상세 조회
	public List<StoreDeliveryDetailsDTO> findByDeliveryCode(String DeliveryCode);
	
	// 납품리스트 기반 입고 자재 상세 조회
	public List<StoreDeliveryDetailsDTO> findByMtDeliveryCode(String DeliveryCode);
	
	// 제품별 사이즈 리스트
    public List<StoreDeliveryDetailsDTO> findBySizeInventory(String productCode);
	
	// 제품코드 기반 옵션 리스트
	public List<Map<String, Object>> findByProductCode(@Param("productCode")String productCode, @Param("list")List<StoreDeliveryDetailsDTO> list, @Param("deliveryCode") String deliveryCode);
	
	// 제품 입고 등록
	public int InsertProduct(@Param("list")SizeDTO SDto, @Param("name")String name);
	
	// 자재 입고 등록
	public int MtInsertProduct(@Param("list")StoreDeliveryDetailsDTO sdto, @Param("name") String name);
	
	// 납품코드 제품 전체 등록 완료 후 상태변환
	public int UpdateDelivery(SizeDTO sizedto);
	
	// 입고수량, 불량처리 수량, 요청수량
	public SizeDTO storeQy();
	
	/**
	 * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 * */
}
