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
	List<Map<String,Object>> pivotProductSupply(@Param("dto") SupplyDTO dto,
            									@Param("sizeList") List<ProductDTO> sizeList);
	List<Map<String,Object>> pivotProductOption(@Param("productCode") String productCode, 
			                                    @Param("sizeList") List<ProductDTO> sizeList); // 빈 양식 (옵션 존재 여부)
	// 공급계획서 등록
	int insertSupplyPlan(Object supply);
	int insertSupplyPlanDetail(Object supply);
}
