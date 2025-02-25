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
		// 들어온 map = { companyArr: ['COM0001',...], COM0001: {header, details},... }
		List<String> companyArr = (List<String>) map.get("companyArr");
		int companyCnt = companyArr.size();
		
		// 들어온 업체만큼 발주서 입력하기 위해 반복문 사용
		for(String companyCode : companyArr) {
			Map<String, Object> companyMap = (Map<String, Object>) map.get(companyCode); // 해당 업체 데이터
			
			// Object의 String타입을 Integer나 Date로 변환할 수 없으므로 DTO로 변환
			Object headerData = companyMap.get("header");
			MaterialOrderDTO headerDto = objMapper.convertValue(headerData, MaterialOrderDTO.class);
			headerDto.setCompanyCode(companyCode); // 없는 값 추가
			int headerResult = mapper.insertMaterialOrder(headerDto);
			
			List<Object> detailList = (List<Object>) companyMap.get("details");
			
			int dtlResult = detailList.size(); // 헤더를 제외한 사이즈를 추출해 비교
			for(Object detail : detailList) {
				MaterialOrderDTO dto = objMapper.convertValue(detail, MaterialOrderDTO.class); // DTO로 변환
				mapper.insertMaterialOrderDetail(dto);
				dtlResult--;
			}
			if(headerResult == 1 && dtlResult == 0) companyCnt--; // 모두 성공하면 숫자 체크
		}
		
		return companyCnt == 0 ? true : false; // 모든 업체에 헤더/디테일 입력 성공했다면 최종 true 반환
	}
	
	@Override
	@Transactional
	public boolean insertMaterialOrderPlan(Map<String, Object> map) {
		// 들어온 map = { companyArr: ['COM0001',...], COM0001: {header, details},... }
		List<String> companyArr = (List<String>) map.get("companyArr");
		int companyCnt = companyArr.size();
		
		return companyCnt == 0 ? true : false; // 모든 업체에 헤더/디테일 입력 성공했다면 최종 true 반환
	}

}
