package com.ezotex.supply.service;

import java.util.List;
import java.util.Map;

import com.ezotex.standard.dto.CompanyDTO;

public interface MaterialOrderService {
	// 발주할 업체 조회
	List<CompanyDTO> listCompanyByProduct(Map<String, String> map);
	int countCompanyByProduct(Map<String, String> map);
	
	// 발주할 자재 및 옵션 조회
	List<Map<String, Object>> listProductByCompany(Map<String, String> map);
	int countProductByCompany(Map<String, String> map);
	
	// 해당 자재의 옵션 조회
	List<Map<String, Object>> listColorByCompany(String productCode, String companyCode);
	
	// 발주서 등록
	boolean insertMaterialOrder(Map<String, Object> map);
	
	// 발주계획서 등록
	boolean insertMaterialOrderPlan(Map<String, Object> map);
}
