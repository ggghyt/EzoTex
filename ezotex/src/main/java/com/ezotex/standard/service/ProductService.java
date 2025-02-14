package com.ezotex.standard.service;

import java.util.List;

import com.ezotex.standard.dto.ProductOptionDTO;

public interface ProductService {
	// 해당 제품 옵션 목록
	List<ProductOptionDTO> listOption(String productCode);
	// 카테고리 목록
	String[] listLclas();
	String[] listSclasByLclas(String lclas);
}
