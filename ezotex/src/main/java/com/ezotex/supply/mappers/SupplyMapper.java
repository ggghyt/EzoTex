package com.ezotex.supply.mappers;

import java.util.List;
import java.util.Map;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.SupplyDTO;

public interface SupplyMapper {
	// 제품 목록 단순조회
	List<ProductDTO> listProduct(ProductDTO product);
	
	// 해당 제품의 옵션 목록 조회 (배열 분리 방식)
	List<ProductDTO> findOptions(String productCode);
	
	// 해당 제품의 공급계획 조회
	List<Map<String,Object>> pivotProductSupply(SupplyDTO supply);
	List<Map<String,Object>> pivotProductOption(String productCode); // 빈 양식
	
	// 공급계획서 등록
	int insertSupplyPlan(Object supply);
	int insertSupplyPlanDetail(Object supply);
}
