package com.ezotex.supply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.supply.dto.MaterialOrderDTO;
import com.ezotex.supply.dto.MaterialOrderPlanDTO;
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
	public List<Map<String, Object>> listColorInfoByCompany(String productCode, String companyCode) {
		return mapper.listColorInfoByCompany(productCode, companyCode);
	}
	
	@Override
	public Map<String, Object> infoMtrByCompany(String productCode, String companyCode) {
		return mapper.infoMtrByCompany(productCode, companyCode);
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
		
		String planCode = (String) map.get("mtrilOrderPlanCode");
		int updateResult = 0;
		if(planCode != null) { // 발주계획서를 참조한 경우 계획서 상태 변경
			MaterialOrderPlanDTO planDto = new MaterialOrderPlanDTO();
			planDto.setStatus("MO02");
			planDto.setMtrilOrderPlanCode(planCode);
			updateResult = mapper.updatePlanState(planDto);
		}
		
		return companyCnt == 0 && planCode == null || 
				(planCode != null && updateResult > 0) ? true : false; // 모든 업체에 헤더/디테일 입력 성공했다면 최종 true 반환
	}
	
	@Override
	@Transactional
	public boolean insertMaterialOrderPlan(Map<String, Object> map) {
		// Object의 String타입을 Integer나 Date로 변환할 수 없으므로 DTO로 변환 필요
		MaterialOrderPlanDTO header = objMapper.convertValue(map.get("headerObj"), MaterialOrderPlanDTO.class); // DTO로 변환
		int headerResult = mapper.insertMaterialOrderPlan(header);
		
		List<Object> detailList = (List<Object>) map.get("detailArr");
		
		int dtlResult = detailList.size(); // 헤더를 제외한 사이즈를 추출해 비교
		for(Object detail : detailList) {
			MaterialOrderPlanDTO dto = objMapper.convertValue(detail, MaterialOrderPlanDTO.class); // DTO로 변환
			mapper.insertMaterialOrderPlanDetail(dto);
			dtlResult--;
		}
		return headerResult == 1 && dtlResult == 0 ? true : false; // 헤더 + 디테일 모두 성공 여부 판단
	}
	
	@Override
	public boolean updatePlanState(MaterialOrderPlanDTO dto) {
		return mapper.updatePlanState(dto) > 0 ? true : false;
	}

	@Override
	public List<MaterialOrderPlanDTO> listOrderPlan(Map<String, Object> map) {
		return mapper.listOrderPlan(map);
	}

	@Override
	public int countOrderPlan(Map<String, Object> map) {
		return mapper.countOrderPlan(map);
	}

	@Override
	public List<MaterialOrderPlanDTO> infoOrderPlan(String mtrilOrderPlanCode) {
		return mapper.infoOrderPlan(mtrilOrderPlanCode);
	}

	@Override
	public List<MaterialOrderDTO> listOrder(Map<String, Object> map) {
		return mapper.listOrder(map);
	}

	@Override
	public List<MaterialOrderDTO> infoOrder(String mtrilOrderCode) {
		return mapper.infoOrder(mtrilOrderCode);
	}

}
