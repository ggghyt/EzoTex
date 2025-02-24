package com.ezotex.supply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.supply.dto.MaterialOrderDTO;
import com.ezotex.supply.mappers.MaterialOrderMapper;
import com.ezotex.supply.service.MaterialOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class MaterialOrderServiceImpl implements MaterialOrderService {

	private final MaterialOrderMapper mapper;
	private final ObjectMapper objMapper;
	
	@Override
	public List<CompanyDTO> listCompanyByProduct(Map<String, String> map) {
		return mapper.listCompanyByProduct(map);
	}
	
	@Override
	public int countCompanyByProduct(Map<String, String> map) {
		return mapper.countCompanyByProduct(map);
	}

	@Override
	public List<Map<String, Object>> listProductByCompany(Map<String, String> map) {
		/*
		// 자재 색상 조회 추가
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		
		List<ProductDTO> mtrList = mapper.listProductByCompany(map);
		mtrList.forEach((mtr) -> {
			List<ProductDTO> colorList = mapper.listColorByCompany(mtr.getProductCode(), map.get("companyCode"));
			// 색상 조회 후 Map으로 변환하여 객체에 필드 추가
			Map<String, Object> objMtr = (Map<String, Object>) objMapper.convertValue(mtr, HashMap.class);
			objMtr.put("colorList", colorList);
			results.add(objMtr);
		});
		
		return results;*/
		return mapper.listProductByCompany(map);
	}

	@Override
	public int countProductByCompany(Map<String, String> map) {
		return mapper.countProductByCompany(map);
	}
	
	@Override
	public List<Map<String, Object>> listColorByCompany(String productCode, String companyCode) {
		return mapper.listColorByCompany(productCode, companyCode);
	}

	@Override
	@Transactional
	public boolean insertMaterialOrder(Map<String, Object> map) {
		// 트랜잭션 커밋/롤백 여부가 정상적으로 반환되는지 확인 필요.
		int headerResult = mapper.insertMaterialOrder(map.get("headerObj"));
		
		// Object의 String타입을 Integer로 변환할 수 없으므로 DTO로 변환 필요
		List<Object> detailList = (List<Object>) map.get("detailArr");
		
		int dtlResult = detailList.size(); // 헤더를 제외한 사이즈를 추출해 비교
		for(Object detail : detailList) {
			MaterialOrderDTO dto = objMapper.convertValue(detail, MaterialOrderDTO.class); // DTO로 변환
			mapper.insertMaterialOrderDetail(dto);
			dtlResult--;
		}
		return headerResult == 1 && dtlResult == 0 ? true : false; // 헤더 + 디테일 모두 성공 여부 판단
	}

}
