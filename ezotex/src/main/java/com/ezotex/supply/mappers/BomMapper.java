package com.ezotex.supply.mappers;

import java.util.List;
import java.util.Map;

import com.ezotex.standard.dto.ProductDTO;

public interface BomMapper {
	// bom 등록할 제품 전체 조회
	List<Map<String, Object>> listBomProduct(ProductDTO product);
	int countBomProduct(ProductDTO product);
	
	// bom 등록할 자재 전체 조회
	List<Map<String, Object>> listBomMaterial(ProductDTO product);
}
