package com.ezotex.standard.service;

public interface ProductService {
	// 카테고리 목록
	String[] listLclas(String productType);
	String[] listSclasByLclas(String lclas);
}
