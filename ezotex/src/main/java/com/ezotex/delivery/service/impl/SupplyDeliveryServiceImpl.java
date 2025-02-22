package com.ezotex.delivery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
import com.ezotex.delivery.mappers.DeliveryMapper;
import com.ezotex.delivery.mappers.SupplyDeliveryMapper;
import com.ezotex.delivery.service.SupplyDeliveryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplyDeliveryServiceImpl implements SupplyDeliveryService {
	
	private final SupplyDeliveryMapper mapper;
	
	 @Autowired
	 private HttpSession session;
	 //String name = (String) session.getAttribute("name");

	@Override
	public List<OrderProductDeliveryDTO> getList(DeliveryRegistSearchDTO searchDTO) {
		return mapper.findAll(searchDTO);
	}

	@Override
	public int getCount(DeliveryRegistSearchDTO searchDTO) {
		return mapper.getCount(searchDTO);
	}

	@Override
	public List<OrderInsertDTO> orderInfo(String orderCode) {
		return mapper.orderInfo(orderCode);
	}

	@Override
	public OrderInfoDTO getAddress(String CompanyCode) {
		OrderInfoDTO oInfo = new OrderInfoDTO();
		oInfo.setAddress(mapper.getAddress(CompanyCode));
		return oInfo;
	}

	@Override
	public String insertDelivery(List<OrderInsertDTO> orderInfoList) {
		
		//출고 코드 가져오기
		String divyCode = mapper.getDeliveryCode();
		orderInfoList.get(orderInfoList.size()-1).setDeliveryCode(divyCode);
		
		//주소 가져오기
		String address = mapper.getAddress(orderInfoList.get(orderInfoList.size()-1).getDivyCompanyCode());
		orderInfoList.get(orderInfoList.size()-1).setStorageName(address);
		
		int time = mapper.getTime(orderInfoList.get(0).getProductOrderCode());
		orderInfoList.get(orderInfoList.size()-1).setTime(time);;
		
		//주문 정보 업데이트
		mapper.updateOrderStatus(orderInfoList.get(orderInfoList.size()-1));
		
		//마스터 정보 입력
		mapper.insertDeliveryMaster(orderInfoList.get(orderInfoList.size()-1));
		
		//디테일 정보 입력하기
		for(int i=0; i<orderInfoList.size()-1; i++) {			
			orderInfoList.get(i).setDeliveryCode(divyCode);
			mapper.insertDeliveryDetails(orderInfoList.get(i));
		}
		
		return "success";
	}
	
	





	
}
