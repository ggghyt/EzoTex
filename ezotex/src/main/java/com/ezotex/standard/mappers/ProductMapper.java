package com.ezotex.standard.mappers;

import java.util.List;

import com.ezotex.standard.dto.ProductOptionDTO;

public interface ProductMapper {
	// 해당 제품 옵션 목록
	List<ProductOptionDTO> listOption(String productCode);
	// 카테고리 목록
	String[] listLclas();
	String[] listSclasByLclas(String lclas);
}
