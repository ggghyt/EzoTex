package com.ezotex.supply.service.impl;

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
	private final ObjectMapper objMapper;

	@Override
	public List<ProductDTO> listProduct(ProductDTO product) {
		return mapper.listProduct(product);
	}

	@Override
	public List<ProductDTO> findOptions(String productCode) {
		return mapper.findOptions(productCode);
	}

	@Override
	public List<Map<String, Object>> pivotProductSupply(SupplyDTO dto) {
		List<ProductDTO> sizeList = mapper.findSize(dto.getProductCode());
		return mapper.pivotProductSupply(dto, sizeList);
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
		// Object의 String타입을 Integer로 변환할 수 없으므로 DTO로 변환 필요
		SupplyDTO header = objMapper.convertValue(supplies.get("headerObj"), SupplyDTO.class); // DTO로 변환
		int headerResult = mapper.insertSupplyPlan(header);
		
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
