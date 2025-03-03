package com.ezotex.store.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.store.dto.DeliverySearchDTO;
import com.ezotex.store.dto.DomListDTO;
import com.ezotex.store.dto.OptionProductDTO;
import com.ezotex.store.dto.ProductInfoListDTO;
import com.ezotex.store.dto.ProductInfoSearchDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.dto.StoreReturnDTO;

public interface StoreMapper {
	
	// 제품 목록 리스트
	public List<ProductInfoListDTO> productInfoList(DeliverySearchDTO searchDTO);
	
	// 제품 목록 total
	public int productInfoTotal(DeliverySearchDTO searchDTO);
	
	// 제품 컬러
	public List<ProductInfoListDTO> productColor(String productCode);
	
	// 제품 사이즈
	public List<ProductInfoListDTO> productSize(@Param("productCode")String productCode ,@Param("productColor")String productColor);
	
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
	
	// 입고 예정 리스트(반품)
	public List<StoreReturnDTO> DeliveryList(DeliverySearchDTO searchDTO);
	
	// 입고 예정 리스트(자재)
	public List<StoreDeliveryDTO> MtDeliveryList(DeliverySearchDTO searchDTO);
	
	// 납품리스트별 총 제품 수량
	//public StoreDeliveryDTO deliveryQy();
	
	// 반품리스트 기반 입고 반품 상세 조회
	public List<StoreReturnDTO> findByDeliveryCode(String returnCode);
	
	// 납품리스트 기반 입고 자재 상세 조회
	public List<StoreDeliveryDetailsDTO> findByMtDeliveryCode(String DeliveryCode);
	
	// 제품별 사이즈 리스트
    public List<StoreDeliveryDetailsDTO> findBySizeInventory(String productCode);
	
	// 제품코드 기반 옵션 리스트
	public List<Map<String, Object>> findByProductCode(@Param("productCode")String productCode, @Param("list")List<StoreDeliveryDetailsDTO> list, @Param("returnCode") String returnCode);
	
	// 옵션 코드 제품명 들고오기
	public OptionProductDTO optionSelect(SizeDTO sdto);
	
	// 창고 제품 위치 등록
	public int InsertOption(@Param("list")SizeDTO SDto, @Param("name")String name, @Param("size")OptionProductDTO size, @Param("id")String id);
	
	// 제품 입고 등록
	public int InsertProduct(@Param("list")SizeDTO SDto, @Param("name")String name);
	
	// 창고 제품 위치 등록(반품)
	public int InsertReturnOption(@Param("list")SizeDTO SDto, @Param("name")String name, @Param("size")OptionProductDTO size, @Param("id")String id);
	
	// 반품 제품 입고 등록
	public int returnInsertProduct(@Param("list")SizeDTO SDto, @Param("name")String name);
	
	// 반품코드기반 남은 제품량
	public int deliveryPrCheck(String returnCode);
	
	// 반환처리완료 상태변환
	public int returnProcessing(String returnCode);
	
	// 자재 입고 등록
	public int MtInsertProduct(@Param("list")StoreDeliveryDetailsDTO sdto, @Param("name") String name);
	
	// 납품코드기반 남은 자재량
	public int deliveryMtCheck(String deliveryCode);
	
	// 납품처리완료 상태변환
	public int deliveryProcessing(String deliveryCode);
	
	// 납품코드 제품 전체 등록 완료 후 상태변환
	public int UpdateDelivery(SizeDTO sizedto);
	
	// 입고수량, 불량처리 수량, 요청수량
	public SizeDTO storeQy();
	
	/**
	 * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 * */
}
