package com.ezotex.supply.service;

import java.util.List;

import com.ezotex.standard.dto.ProductDTO;

public interface SupplyService {
	// 제품 목록 단순조회
	List<ProductDTO> listProduct(ProductDTO product);
}
