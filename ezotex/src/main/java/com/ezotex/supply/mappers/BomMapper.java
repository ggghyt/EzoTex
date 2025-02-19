package com.ezotex.supply.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.BomDTO;

public interface BomMapper {
	// bom 등록할 제품 전체 조회
	List<Map<String, Object>> listBomProduct(ProductDTO product);
	int countProduct(ProductDTO product);
	
	// 선택한 제품의 옵션 조회
	List<ProductDTO> listColor(String productCode);
	List<ProductDTO> listSizeByColor(@Param("productCode") String productCode, @Param("productColor") String productColor);
	
	// bom 등록할 자재 전체 조회
	List<BomDTO> listBomMaterial(ProductDTO product);
	
	// bom 등록
	int insertBom(Object bom);
	int insertBomDetail(Object bom);
}
