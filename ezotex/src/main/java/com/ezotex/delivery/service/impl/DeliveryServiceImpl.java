package com.ezotex.delivery.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.comm.ezoTexCustomException.LotNotFoundException;
import com.ezotex.delivery.dto.DeliveryProductInfo;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
import com.ezotex.delivery.mappers.DeliveryMapper;
import com.ezotex.delivery.service.DeliveryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
	
	private final DeliveryMapper mapper;
	
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
	public List<OrderInfoDTO> getOrderInfo(String prdOrderCode) {
		return mapper.getOrderInfo(prdOrderCode);
	}

	@Override
	public Map<String, Object> findByProductCode(String productCode, String orderCode) {
		Map<String, Object> map = new HashMap<>();
		
		List<DeliveryProductInfo> list = mapper.findBySize(productCode);
		
		map.put("optionList", list);
		map.put("qyList", mapper.sizeFindByProductCode(productCode, list, orderCode));
		
		return map;
	}
	
	@Override
	@Transactional
	public String insertDelivery(List<OrderInsertDTO> orderInfoList) {
		
		//가장 최근 배송코드 가져오기, 객체에 저장
		String deliveryCode = mapper.getDeliveryCode();
		orderInfoList.get(orderInfoList.size()-1).setDeliveryCode(deliveryCode);
		log.info("============================================================배송코드 가져오기 완료", deliveryCode);
		
		//주문건에 몇회차 출고건인지 가져오기 첫번째면 0, 객체에 저장
		int deliveryTime = mapper.getTime((orderInfoList.get(orderInfoList.size()-1)).getProductOrderCode());
		int time =  deliveryTime == 0 ? 1 : deliveryTime;
		orderInfoList.get(orderInfoList.size()-1).setTime(time);
		
		//출고 마스터 정보 입력
		mapper.insertDeliveryMaster(orderInfoList.get(orderInfoList.size()-1));
		
		//주문 정보 업데이트
		mapper.updateOrderStatus(orderInfoList.get(orderInfoList.size()-1));
		
		//자재 가져오기(리스트 마지막이 주문 정보라서 그 전까지만 반복문 실행)
		for(int i=0; i<orderInfoList.size()-1; i++	) {	
			//요청수량
			int reqQy = orderInfoList.get(i).getReqQy();
			
			int divyQy = orderInfoList.get(i).getDeliveryQy();
			//로트 검색
			List<OrderInsertDTO> lotList = mapper.getProductLot(orderInfoList.get(i));
			//if(lotList.size() <= 0) return "fail";	//로트 수량 없을경우 바로 리턴하면 안됨. 0으로 의도적으로 보낼수도 있음.
			/*
			if(lotList.size() <= 0) {
				throw new LotNotFoundException("fail");
			}
			*/
			//로트 재고 수량 총합을 받을 변수
			int checkSum = 0;
			
			//로트 재고 수량 총합 구하기
			for(int j=0; j<lotList.size(); j++) {
				checkSum += lotList.get(j).getLotQy();
			}
			
			//로트 재고 수량 총합이 출고수량보다 작으면 바로 종료
			//if(checkSum < reqQy) return "fail";			//-------------------------이 조건에 걸리면 전부 롤백해야됨.
			if(divyQy != 0 && checkSum < divyQy) {
				System.out.println("==================================================================작동됨" + divyQy + "재고 수량 합계" + checkSum);
				throw new LotNotFoundException("fail");
			}
			
			//출고 수량이 0이면 다음 반복문을 실행함.
			if(divyQy == 0) {
				break;
			}
			//로트 수량 총합을 구할 변수(계산용)
			int sumLotQy = 0;
			
			//가져온 로트 수만큼 반복문 실행
			for(int j=0; j<lotList.size(); j++) {
				
				int lotQy = lotList.get(j).getLotQy();
				//로트에 있는 재고 수량을 더함.
				
				sumLotQy += lotQy;
				
				//실제 insert할 데이터를 객체에 담음
				OrderInsertDTO insertInfo = new OrderInsertDTO();

				//실제 출고할 수량. 로트수량이 요청 수량보다 많을경우을 대비해서 수량 임의 설정
				//출고 수량 - 로트 수량이 0보다 작을때(출고 수량이 로트 수량보다 작을때는) 
				int insertDivyQy = (divyQy - lotQy) < 0 ? lotQy - Math.abs(divyQy - lotQy) : lotQy;
				
				insertInfo.setDeliveryCode(deliveryCode);	//출고 코드
				insertInfo.setProductCode(orderInfoList.get(i).getProductCode());	//제품 코드
				insertInfo.setProductSize(orderInfoList.get(i).getProductSize());	//제품 사이즈
				insertInfo.setProductColor(orderInfoList.get(i).getProductColor());	//제품 색상
				insertInfo.setProductLot(lotList.get(j).getProductLot());			//로트 수량
				insertInfo.setReqQy(orderInfoList.get(i).getReqQy());	//요청 수량
				insertInfo.setDeliveryQy(insertDivyQy);	//출고 수량
				insertInfo.setProductSe("PT02");	//제품 구분 "제품"
				insertInfo.setProductOrderCode((orderInfoList.get(orderInfoList.size()-1)).getProductOrderCode());	//제품 주문번호
				
				//제품 단가, 금액 가져오기 
				List<OrderInsertDTO> priceInfo = mapper.getPrice(insertInfo);
				for(int n=0; n<priceInfo.size(); n++) {
					insertInfo.setUnitPrice(priceInfo.get(n).getUnitPrice());	//단가
					insertInfo.setPrice(priceInfo.get(n).getPrice());			//금액
				}
				
				/*출고 수량 인서트*/
				mapper.insertDeliveryDetails(insertInfo);
				
				/*재고 수량 업데이트*/
				mapper.updateLotQy(insertInfo);
				
				//요청 수량에서 로트 합을 계속 빼기
				reqQy -= sumLotQy;
				
				//로트 합을 계속 빼서, 0보다 작아지면 바로 종료함.
	            if(reqQy <= 0) {
	                break;
	            };
				
			}
			
		}
		
		//로트 재고 수량이 충분한 경우 아래 값 보냄
		return "success";
	}



	
}
