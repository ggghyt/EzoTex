package com.ezotex.supply.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.SupplyDTO;

public interface SupplyMapper {
	// 제품 목록 단순조회
	List<ProductDTO> listProduct(ProductDTO product);
	
	// 해당 제품의 옵션 목록 조회 (사이즈/색상 중 한 가지만 있거나 단일 제품인 경우 필요)
	List<ProductDTO> findOptions(String productCode);
	
	// 제품의 모든 사이즈 조회
	List<ProductDTO> findSize(String productCode);
	
	// 해당 제품의 공급계획 조회
	List<Map<String,Object>> pivotProductSupply(@Param("dto") Map<String, Object> dto, // 색상, 사이즈로 조회
            									@Param("sizeList") List<ProductDTO> sizeList);
	List<Map<String,Object>> listProductSupply(Map<String, Object> dto);
	
	// 빈 피벗양식 (옵션 존재 여부 포함)
	List<Map<String,Object>> pivotProductOption(@Param("productCode") String productCode, 
			                                    @Param("sizeList") List<ProductDTO> sizeList);

	
	// 공급계획서 조회
	List<SupplyDTO> listSupplyPlan(Map<String, Object> map);
	int countSupplyPlan(Map<String, Object> map);
	

	// 공급계획서 단건조회
	List<SupplyDTO> infoSupplyPlan(String supplyPlanCode);
  
	// 공급계획서 등록
	int insertSupplyPlan(Object supply);
	int insertSupplyPlanDetail(Object supply);
}
