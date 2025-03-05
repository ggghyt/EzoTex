package com.ezotex.delivery.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.PackingDTO;
import com.ezotex.comm.ezoTexCustomException.LotNotFoundException;
import com.ezotex.delivery.dto.DeliveryAllCharger;
import com.ezotex.delivery.dto.DeliveryProductInfo;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.DeliveryScheduleDTO;
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
	
	//사이즈 피벗, 요청 제품 옵션 리스트
	@Override
	public Map<String, Object> findByProductCode(String productCode, String orderCode) {
		Map<String, Object> map = new HashMap<>();
		
		List<DeliveryProductInfo> list = mapper.findBySize(productCode);
		List<Map<String, Object>> pivot = mapper.sizeFindByProductCode(productCode, list, orderCode);
		
		//화면으로 보낼 데이터를 만들어서 넣을 새로운 list변수
		List<Map<String, Object>> sendData = new ArrayList<Map<String,Object>>();
		
		//리스트의 각 map에서 요청수량이 있으면 새로운 리스트에 담아서 보내기
		for(int i=0; i<list.size(); i++) {
			//한 제품의 옵션리스트 = list
			String targetSize = "RE" + list.get(i).getShowSize();	//옵션리스트에 있는 사이즈를 가져와서 RE문자열을 붙이면 요청수량을 가져올 수 있음
			
			//옵션을 기반으로 가져온 피벗 내용 개수만큼 반복문
			for(int j=0; j<pivot.size(); j++) {
				//요청 수량이 있으면 널이 아님.
				if(pivot.get(j).get(targetSize) != null) {
					//요청수량이 있는것만 화면으로 보냄
					sendData.add(pivot.get(j));
				}
			}
		}
		
		map.put("optionList", list);
		map.put("qyList", sendData);
		
		return map;
	}
	
	@Override
	@Transactional
	public String insertDelivery(List<OrderInsertDTO> orderInfoList) {
		
		//가장 최근 배송코드 가져오기, 객체에 저장
		String deliveryCode = mapper.getDeliveryCode();
		orderInfoList.get(orderInfoList.size()-1).setDeliveryCode(deliveryCode);
		
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
			
			//중간에 로트 수량이 없는 경우에는 디테일 정보를 입력하지 않고 다음으로 넘어감
			if(divyQy == 0) {
				continue;
			}
			
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
				divyQy -= sumLotQy;
				
				//로트 합을 계속 빼서, 0보다 작아지면 바로 종료함.
	            if(divyQy <= 0) {
	                break;
	            };
				
			}
			
		}
		
		//로트 재고 수량이 충분한 경우 아래 값 보냄
		return "success";
	}

	@Override
	public List<OrderProductDeliveryDTO> deliveryList(DeliveryRegistSearchDTO searchDTO) {
		return mapper.deliveryList(searchDTO);
	}

	@Override
	public int deliveryListCnt(DeliveryRegistSearchDTO searchDTO) {
		return mapper.deliveryListCnt(searchDTO);
	}

	//출고 단건조회(출고 제품 + 담당자 리스트)
	@Override
	public Map<String, Object> deliveryInfo(String deliveryCode) {
		DeliveryAllCharger charger = mapper.allcharger(deliveryCode);
		
		List<DeliveryProductInfo> productList = mapper.deliveryProductList(deliveryCode);
		//담당자 정보
		Map<String, Object> map = new HashMap<>();
		map.put("chargerInfo", charger);
		map.put("productList", productList);
		
		return map;
	}
	
	//출고 제품 조회
	public List<DeliveryProductInfo> deliveryProductListWithLot(String deliveryCode) { 
		return mapper.deliveryProductList(deliveryCode);
	}
	
	@Override
	public Map<String, Object> deliveryProductDetails(String deliveryCode, String productCode) {
		Map<String, Object> map = new HashMap<>();
		
		List<DeliveryProductInfo> list = mapper.findBySize(productCode);

		map.put("optionList", list);
		map.put("qyList", mapper.deliveryProductDetails(productCode, list, deliveryCode));
		return map;
	}

	@Override
	public List<OrderInsertDTO> deliveryLot(OrderInsertDTO info) {
		return mapper.deliveryLot(info);
	}

	@Override
	public Map<String, Object> packingSTDInfo() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("storage", mapper.getStorageInfo());	//창고 리스트
		map.put("box", mapper.getBoxInfo());			//상자 리스트
		
		return map;
	}

	//포장 등록
	@Override
	@Transactional
	public String insertPackingInfo(List<PackingDTO> info) {
		
		//포장내용 등록
		for(int i=0; i<info.size(); i++) {			
			log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++인서트 반복문");
			mapper.insertPackingInfo(info.get(i));
		}
		
		mapper.updateStorageName(info.get(0));
		return "success";
	}
	
	//출고 스케줄등록
	@Override
	@Transactional
	public String insertDivySchedule(DeliveryScheduleDTO info) {
		String name = (String) session.getAttribute("name");
		String code = (String) session.getAttribute("code");
		
		info.setChargerCode(code);
		info.setChargerName(name);
		
		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info(info.toString());
		mapper.insertDivySchedule(info);
		
		
		return "success";
	}

	@Override
	public List<OrderProductDeliveryDTO> packingList(DeliveryRegistSearchDTO searchDTO) {
		return mapper.packingList(searchDTO);
	}

	@Override
	public int packingListCnt(DeliveryRegistSearchDTO searchDTO) {
		return mapper.packingListCnt(searchDTO);
	}

	
	
}
