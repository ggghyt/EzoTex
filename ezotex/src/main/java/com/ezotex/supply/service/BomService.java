package com.ezotex.supply.service;

import java.util.List;
import java.util.Map;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.BomDTO;

public interface BomService {
	// bom 등록할 제품 전체 조회
	List<Map<String, Object>> listBomProduct(ProductDTO product);
	int countProduct(ProductDTO product);
	
	// 선택한 제품의 옵션 조회
	List<ProductDTO> listColor(String productCode);
	List<ProductDTO> listSizeByColor(String productCode, String productColor);
	
	// bom 등록할 자재 전체 조회
	List<BomDTO> listBomMaterial(ProductDTO product);
	
	// bom 등록
	boolean insertBom(Map<String, Object> boms);
}
