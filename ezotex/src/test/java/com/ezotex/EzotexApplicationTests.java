package com.ezotex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ezotex.delivery.dto.DeliveryProductInfo;
import com.ezotex.delivery.mappers.DeliveryMapper;
import com.ezotex.supply.mappers.MaterialOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class EzotexApplicationTests {

	@Autowired
	DeliveryMapper mapper;
	
	//사이즈 피벗, 요청 제품 옵션 리스트
		public Map<String, Object> findByProductCode(String productCode, String orderCode) {
			Map<String, Object> map = new HashMap<>();
			
			List<DeliveryProductInfo> list = mapper.findBySize(productCode);
			List<Map<String, Object>> pivot = mapper.sizeFindByProductCode(productCode, list, orderCode);
			
			
			//화면으로 보낼 데이터를 만들어서 넣을 새로운 list변수
			List<Map<String, Object>> sendData = new ArrayList<Map<String,Object>>();
			
			//중복을 없애주기 위한 set선언
			Set<Map<String, Object>> setList = new HashSet<Map<String, Object>>();
			
			
			//리스트의 각 map에서 요청수량이 있으면 새로운 set에 담기(요청한 제품의 정보만 저장 + 중복 제거)
			for(int i=0; i<list.size(); i++) {
				//한 제품의 옵션리스트 = list
				String targetSize = "RE" + list.get(i).getShowSize();	//옵션리스트에 있는 사이즈를 가져와서 RE문자열을 붙이면 요청수량을 가져올 수 있음
				
				//옵션을 기반으로 가져온 피벗 내용 개수만큼 반복문
				for(int j=0; j<pivot.size(); j++) {
					
					//요청 수량이 있으면 널이 아님.
					if(pivot.get(j).get(targetSize) != null) {
						setList.add(pivot.get(j));
						
					}
				}
				
			}
			
			
			
			/*
			for(int i=0; i<pivot.size(); i++) {
			    Set<Map<String, Object>> set = pivot.get(i);  // Set을 가져옴
			    Iterator<Map<String, Object>> iterSet = set.iterator();  // Iterator로 변환
			    
			    while (iterSet.hasNext()) {
			        Map<String, Object> info = iterSet.next();  // 데이터 가져오기
			        sendData.add(info);
			    }
			}
			*/
			
			log.info("==============================================화면으로 보낼 요청 수량 데이터");
			log.info(sendData.toString());
			
			map.put("optionList", list);
			map.put("qyList", sendData);
			
			return map;
		}

}
