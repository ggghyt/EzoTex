package com.ezotex.supply.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.standard.dto.ProductDTO;

public interface MaterialOrderMapper {
	// 발주할 업체 조회
	List<CompanyDTO> listCompanyByProduct(Map<String, String> map);
	int countCompanyByProduct(Map<String, String> map);
	
	// 발주할 자재 조회
	List<ProductDTO> listProductByCompany(Map<String, String> map);
	int countProductByCompany(Map<String, String> map);
	
	// 자재의 옵션 조회
	List<ProductDTO> listColorByCompany(@Param("productCode") String productCode, @Param("companyCode") String companyCode);
	
	// 발주서 등록
	int insertMaterialOrder(Object dto);
	int insertMaterialOrderDetail(Object dto);
}
