package com.ezotex.delivery.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
import com.ezotex.delivery.dto.OrderProductDeliveryDTO;


/*
 * 공급업체 관련 매퍼
 * */
public interface SupplyDeliveryMapper {
	//검색 결과 리스트
	public List<OrderProductDeliveryDTO> findAll(@Param("searchDto") DeliveryRegistSearchDTO searchDto);
	
	//검색 결과 총 레코드 수
	public int getCount(@Param("searchDto") DeliveryRegistSearchDTO searchDto);
	
	//발주 상세내용
	public List<OrderInsertDTO> orderInfo(@Param("orderCode")String orderCode);
	
	//주소 가져오기
	public String getAddress(@Param("companyCode")String companyCode);
	
	//출고번호 최근+1가져오기, 
    public String getDeliveryCode();
    
    //분할출고 수 출력
    public int getTime(@Param("productOrderCode")String productOrderCode);
    
    //(공급업체 사용)출고 등록
    public void insertDeliveryMaster(@Param("info")OrderInsertDTO info);
    
    //(공급업체에서 사용)출고 제품 등록
    public void insertDeliveryDetails(@Param("pinfo")OrderInsertDTO pinfo);
       	
    //주문 상태 업데이트
    public void updateOrderStatus(@Param("info")OrderInsertDTO pinfo);
    
    //출고 내역 조회
    public List<OrderProductDeliveryDTO> supplyDeliveryList(@Param("cri")DeliveryRegistSearchDTO searchDto);
    
    //출고 내역 조회 총 레코드 수
    public int supplyDeliveryListCount(@Param("cri")DeliveryRegistSearchDTO searchDto);
    
    //출고 단건조회
    public List<OrderInfoDTO> deliveryInfo(@Param("deliveryCode")String deliveryCode);
    
}
