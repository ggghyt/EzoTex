package com.ezotex.supply.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.supply.dto.MaterialOrderPlanDTO;

public interface MaterialOrderMapper {
	// 발주할 업체 조회
	List<CompanyDTO> listCompanyByProduct(Map<String, String> map);
	int countCompanyByProduct(Map<String, String> map);
	
	// 발주할 자재 조회
	List<Map<String, Object>> listProductByCompany(Map<String, String> map);
	int countProductByCompany(Map<String, String> map);
	
	// 해당 자재의 옵션 조회
	List<Map<String, Object>> listColorByCompany(@Param("productCode") String productCode, @Param("companyCode") String companyCode);
	
	// 발주서 등록
	int insertMaterialOrder(Object dto);
	int insertMaterialOrderDetail(Object dto);
	
	// 발주계획서 등록
	int insertMaterialOrderPlan(Object dto);
	int insertMaterialOrderPlanDetail(Object dto);
	
	// 발주계획서 조회
	List<MaterialOrderPlanDTO> listOrderPlan(Map<String, Object> map);
	int countOrderPlan(Map<String, Object> map);
	
	// 발주계획서 조회(단건)
	List<MaterialOrderPlanDTO> infoOrderPlan(String mtrilOrderPlanCode);
}
