package com.ezotex.supply.mappers;

import java.util.List;

import com.ezotex.standard.dto.ProductDTO;

public interface SupplyMapper {
	// 제품 목록 단순조회
	List<ProductDTO> listProduct(ProductDTO product);
	
	
}
