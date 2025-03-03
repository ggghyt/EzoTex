package com.ezotex.supply.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
	private final ObjectMapper objMapper;

	@Override
	public List<Map<String, Object>> listBomProduct(ProductDTO product) {
		return mapper.listBomProduct(product);
	}

	@Override
	public int countProduct(ProductDTO product) {
		return mapper.countProduct(product);
	}
	
	@Override
	public List<ProductDTO> listSize(String productCode) {
		return mapper.listSize(productCode);
	}
	
	@Override
	public List<ProductDTO> listColor(String productCode) {
		return mapper.listColor(productCode);
	}

	@Override
	public List<ProductDTO> listSizeByColor(String productCode, String productColor) {
		return mapper.listSizeByColor(productCode, productColor);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> listBomMaterial(ProductDTO product) {
		// 자재 색상 조회 추가
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		
		List<BomDTO> mtrList = mapper.listBomMaterial(product);
		mtrList.forEach((mtr) -> {
			List<ProductDTO> colorList = mapper.listColor(mtr.getMtrilCode());
			// 색상 조회 후 Map으로 변환하여 객체에 필드 추가
			Map<String, Object> objMtr = (Map<String, Object>) objMapper.convertValue(mtr, HashMap.class);
			objMtr.put("colorList", colorList);
			results.add(objMtr);
		});
		
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean insertBom(Map<String, Object> boms) {
		int bomResult = mapper.insertBom(boms.get("headerObj"));
		
		// Object의 String타입을 Integer로 변환할 수 없으므로 DTO로 변환 
		List<Object> detailList = (List<Object>) boms.get("detailArr");
		
		int dtlResult = detailList.size(); // 헤더를 제외한 사이즈를 추출해 비교
		for(Object detail : detailList) {
			BomDTO bom = objMapper.convertValue(detail, BomDTO.class); // DTO로 변환
			mapper.insertBomDetail(bom);
			dtlResult--;
		}
		return bomResult == 1 && dtlResult == 0 ? true : false; // 헤더 + 디테일 모두 성공 여부 판단
	}

	@Override
	public boolean checkBomAllInserted(String productCode) {
		return mapper.checkBomNull(productCode) != null ? false : true;
	}
	
}
