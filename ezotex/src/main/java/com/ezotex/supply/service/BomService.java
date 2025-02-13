package com.ezotex.supply.service;

import java.util.List;
import java.util.Map;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.BomDTO;

public interface BomService {
	List<BomDTO> listBom();
	BomDTO infoBom(String bomCode);
	List<Map<String, Object>> listBomProduct(ProductDTO product);
	int countBomProduct(ProductDTO product);
}
