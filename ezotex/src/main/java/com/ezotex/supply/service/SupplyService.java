package com.ezotex.supply.service;

import java.util.List;
import java.util.Map;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.SupplyDTO;

public interface SupplyService {
	// 제품 목록 단순조회
	List<ProductDTO> listProduct(ProductDTO product);

	// 해당 제품의 옵션 목록 조회 (사이즈/색상 중 한 가지만 있거나 단일 제품인 경우 필요)
	List<ProductDTO> findOptions(String productCode);
	
	// 해당 제품의 공급계획 조회
	List<Map<String,Object>> pivotProductSupply(Map<String, Object> dto);
	
	// 빈 피벗양식 (옵션 존재 여부 포함)
	List<Map<String,Object>> pivotProductOption(String productCode); 
	
	// 공급계획서 조회
	List<SupplyDTO> listSupplyPlan(Map<String, Object> map);
	int countSupplyPlan(Map<String, Object> map);
	
	// 공급계획서 단건조회
	List<SupplyDTO> infoSupplyPlan(Map<String, String> map);
	
	// 공급계획서 등록
	boolean insertSupplyPlan(Map<String, Object> supplies);

	// 공급계획서 수정
	boolean updateSupplyPlan(Map<String, Object> supplies);
	
	// 자재소요계획 조회
	List<SupplyDTO> listMrp(SupplyDTO dto);
}
