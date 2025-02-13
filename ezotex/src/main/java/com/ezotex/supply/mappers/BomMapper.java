package com.ezotex.supply.mappers;

import java.util.List;
import java.util.Map;

import com.ezotex.comm.dto.SearchDTO;
import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.BomDTO;

public interface BomMapper {
	List<BomDTO> listBom();
	BomDTO infoBom(String bomCode);
	List<Map<String, Object>> listBomProduct(ProductDTO product);
	int countBomProduct(ProductDTO product);
}
