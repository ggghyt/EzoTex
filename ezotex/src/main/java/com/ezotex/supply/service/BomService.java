package com.ezotex.supply.service;

import java.util.List;
import java.util.Map;

import com.ezotex.standard.dto.ProductDTO;

public interface BomService {
	// bom 등록할 제품 전체 조회
	List<Map<String, Object>> listBomProduct(ProductDTO product);
	int countProduct(ProductDTO product);
	
	// 선택한 제품의 옵션 조회
	List<ProductDTO> listSize(String productCode);
	List<ProductDTO> listColor(String productCode);
	List<ProductDTO> listSizeByColor(String productCode, String productColor);
	
	// bom 등록할 자재 + 색상 전체 조회
	List<Map<String, Object>> listBomMaterial(ProductDTO product);
	
	// bom 등록
	boolean insertBom(Map<String, Object> boms);
	
	// BOM 등록 이후 페이지에서 모든 옵션 완료됐는지 확인하기 위함
	boolean checkBomAllInserted(String productCode);
}
