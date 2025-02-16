package com.ezotex.standard.mappers;

public interface ProductMapper {
	// 카테고리 목록
	String[] listLclas(String productType);
	String[] listSclasByLclas(String lclas);
}
