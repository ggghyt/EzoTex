package com.ezotex.delivery.mappers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.PackingDTO;
import com.ezotex.delivery.dto.DeliveryAllCharger;
import com.ezotex.delivery.dto.DeliveryProductInfo;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.DeliveryScheduleDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;

public interface DeliveryMapper {
	
	//주문 등록건 전체 조회
	public List<OrderProductDeliveryDTO> findAll(@Param("cri") DeliveryRegistSearchDTO cri);
	
	//주문 등록건 전체 개수(페이징)
	public int getCount(@Param("cri") DeliveryRegistSearchDTO searchDTO);
	
	//주문 등록건 단건조회
	public List<OrderInfoDTO> getOrderInfo(String prdOrderCode);
	
	// 제품별 사이즈 리스트
    public List<DeliveryProductInfo> findBySize(String productCode);
	
	// 제품코드 기반 옵션 리스트
    public List<Map<String, Object>> sizeFindByProductCode(@Param("productCode")String productCode, 
															@Param("list")List<DeliveryProductInfo> list,
															@Param("orderCode")String orderCode);
    
    
    //(제조업체에서 사용) 주문건 상태 변환
    public void updateOrderStatus(@Param("info")OrderInsertDTO info);
    
    //출고번호 최근+1가져오기, 
    public String getDeliveryCode();
    
    //분할출고 수 출력
    public int getTime(@Param("productOrderCode")String productOrderCode);
    
    //(제조업체에서 사용)출고 등록
    public void insertDeliveryMaster(@Param("info")OrderInsertDTO info);
    					
    //로트검색 
    public List<OrderInsertDTO> getProductLot(@Param("pinfo")OrderInsertDTO pinfo);
    
    //(제조업체에서 사용)출고 제품 등록
    public void insertDeliveryDetails(@Param("divyQy")int pinfo,
    								  @Param("lotCode")String lotCode);
    
    //로트 수량 업데이트
    public void updateLotQy(@Param("deInfo")OrderInsertDTO deinfo);
    
    //제품 단가, 금액 가져오기
    public List<OrderInsertDTO> getPrice(@Param("pinfo")OrderInsertDTO pinfo);
    
    //납품조회
    public List<OrderProductDeliveryDTO> deliveryList(@Param("cri") DeliveryRegistSearchDTO search);
    
    //납품조회 카운트
    public int deliveryListCnt(@Param("cri") DeliveryRegistSearchDTO searchDTO);
    
	//포장 조회
	public List<OrderProductDeliveryDTO> packingList(@Param("cri") DeliveryRegistSearchDTO searchDTO);
	
	//포장 조회 카운트
	public int packingListCnt(@Param("cri") DeliveryRegistSearchDTO searchDTO);
	
    //납품 관련 모든 담당자
    public DeliveryAllCharger allcharger(@Param("deliveryCode")String deliveryCode);
    
    //납품 제품
    public List<DeliveryProductInfo> deliveryProductList(@Param("deliveryCode")String deliveryCode);
    
    //납품 제품 상세(피벗)
    public List<Map<String, Object>> deliveryProductDetails(@Param("productCode")String productCode, 
			@Param("list")List<DeliveryProductInfo> list,
			@Param("deliveryCode")String deliveryCode);
    
    //납품 로트
    public List<OrderInsertDTO> deliveryLot(@Param("info")OrderInsertDTO info);
    
    //상자 정보
    public List<OrderInsertDTO> getBoxInfo();
    
    //창고 리스트 가져오기
    public List<String> getStorageInfo();
    
    //포장 정보 등록하기
    public int insertPackingInfo(@Param("info")PackingDTO info);
    
    //창고명 업데이트
    public int updateStorageName(@Param("info")PackingDTO info);
    
    //배송 스케줄등록하기
    public int insertDivySchedule(@Param("info")DeliveryScheduleDTO info);
    
}
