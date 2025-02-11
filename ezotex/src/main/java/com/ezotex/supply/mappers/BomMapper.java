package com.ezotex.supply.mappers;

import java.util.List;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.BomDTO;

public interface BomMapper {
	List<BomDTO> listBom();
	BomDTO infoBom(String bomCode);
	List<ProductDTO> listProductBom();
}
