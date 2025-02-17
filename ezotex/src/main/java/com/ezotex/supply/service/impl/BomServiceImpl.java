package com.ezotex.supply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.BomDTO;
import com.ezotex.supply.mappers.BomMapper;
import com.ezotex.supply.service.BomService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BomServiceImpl implements BomService {
	
	private final BomMapper mapper;

	@Override
	public List<Map<String, Object>> listBomProduct(ProductDTO product) {
		return mapper.listBomProduct(product);
	}

	@Override
	public int countBomProduct(ProductDTO product) {
		return mapper.countBomProduct(product);
	}

	@Override
	public List<ProductDTO> listColor(String productCode) {
		return mapper.listColor(productCode);
	}

	@Override
	public List<ProductDTO> listSizeByColor(String productCode, String productColor) {
		return mapper.listSizeByColor(productCode, productColor);
	}

	@Override
	public List<BomDTO> listBomMaterial(ProductDTO product) {
		return mapper.listBomMaterial(product);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean insertBom(Map<String, Object> boms) {
		// 트랜잭션 커밋/롤백 여부가 정상적으로 반환되는지 확인 필요.
		int bomResult = mapper.insertBom(boms.get("headerObj"));
		
		// Object의 String타입을 Integer로 변환할 수 없음.
		// Object => DTO로 변환 필요
		ObjectMapper objMapper = new ObjectMapper();
		List<Object> detailList = (List<Object>) boms.get("detailArr");
		
		int dtlResult = detailList.size(); // 헤더를 제외한 사이즈를 추출해 비교
		for(Object detail : detailList) {
			BomDTO bom = objMapper.convertValue(detail, BomDTO.class);
			mapper.insertBomDetail(bom);
			dtlResult--;
		}
		return bomResult == 1 && dtlResult == 0 ? true : false; // 헤더 + 디테일 모두 성공 여부 판단
	}
	
}
