package com.ezotex.supply.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.SupplyDTO;
import com.ezotex.supply.mappers.SupplyMapper;
import com.ezotex.supply.service.SupplyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplyServiceImpl implements SupplyService {
	
	private final SupplyMapper mapper;

	@Override
	public List<ProductDTO> listProduct(ProductDTO product) {
		return mapper.listProduct(product);
	}

	@Override
	public Map<String,List<Map<String,Object>>> findOptions(String productCode) {
		List<ProductDTO> optList = mapper.findOptions(productCode);
		log.info(optList.toString());
		
		Map<String,List<Map<String,Object>>> colorMap = new HashMap<String,List<Map<String,Object>>>();
		
		// WHITE=[{size=SI01, sizeNm=S},....] 형태로 추출
		for(ProductDTO opt : optList) {
			String color = opt.getProductColor();
			String size = opt.getProductSize();
			String sizeNm = opt.getSizeName();

			if(!colorMap.containsKey(color)) { // 없는 값인 경우 키 추가
				colorMap.put(color, new ArrayList<>());
			} else { // 있으면 컬러에 사이즈, 사이즈명 추가
				Map<String,Object> sizeMap = new HashMap<String,Object>();
				sizeMap.put("size", size);
				sizeMap.put("sizeNm", sizeNm);
				colorMap.get(color).add(sizeMap);
			}
		}
		return colorMap;
	}

	@Override
	public List<Map<String, Object>> pivotProductSupply(SupplyDTO dto) {
		List<ProductDTO> sizeList = mapper.findSize(dto.getProductCode());
		List<Map<String, Object>> map = mapper.pivotProductSupply(dto, sizeList);
		List<Map<String, Object>> compareMap = mapper.pivotProductOption(dto.getProductCode(), sizeList);
		return map;
		
	}

	@Override // 옵션에 따른 빈 양식 반환
	public List<Map<String, Object>> pivotProductOption(String productCode) {
		List<ProductDTO> sizeList = mapper.findSize(productCode);
		return mapper.pivotProductOption(productCode, sizeList);
	}	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean insertSupplyPlan(Map<String, Object> supplies) {
		// 트랜잭션 커밋/롤백 여부가 정상적으로 반환되는지 확인 필요.
		int headerResult = mapper.insertSupplyPlan(supplies.get("headerObj"));
		
		// Object의 String타입을 Integer로 변환할 수 없으므로 DTO로 변환 필요
		ObjectMapper objMapper = new ObjectMapper();
		List<Object> detailList = (List<Object>) supplies.get("detailArr");
		
		int dtlResult = detailList.size(); // 헤더를 제외한 사이즈를 추출해 비교
		for(Object detail : detailList) {
			SupplyDTO dto = objMapper.convertValue(detail, SupplyDTO.class); // DTO로 변환
			mapper.insertSupplyPlanDetail(dto);
			dtlResult--;
		}
		return headerResult == 1 && dtlResult == 0 ? true : false; // 헤더 + 디테일 모두 성공 여부 판단
	}
	
}
