package com.ezotex.supply.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.supply.dto.MaterialOrderDTO;
import com.ezotex.supply.dto.MaterialOrderPlanDTO;

public interface MaterialOrderService {
	// 발주할 업체 조회
	List<CompanyDTO> listCompanyByProduct(Map<String, String> map);
	int countCompanyByProduct(Map<String, String> map);
	
	// 발주할 자재 및 옵션 조회
	List<Map<String, Object>> listProductByCompany(Map<String, String> map);
	int countProductByCompany(Map<String, String> map);
	
	// 해당 자재의 옵션별 정보 조회 (색상, 안전재고 등)
	List<Map<String, Object>> listColorInfoByCompany(String productCode, String companyCode);
	// 옵션이 없을 경우 단건 조회
	Map<String, Object> infoMtrByCompany(@Param("productCode") String productCode, @Param("companyCode") String companyCode);
	
	// 발주서 등록
	boolean insertMaterialOrder(Map<String, Object> map);
	
	// 발주계획서 등록
	boolean insertMaterialOrderPlan(Map<String, Object> map);

	// 발주계획서 수정
	boolean updatePlanState(MaterialOrderPlanDTO dto);
	
	// 발주계획서 조회
	List<MaterialOrderPlanDTO> listOrderPlan(Map<String, Object> map);
	int countOrderPlan(Map<String, Object> map);
	
	// 발주계획서 조회(단건)
	List<MaterialOrderPlanDTO> infoOrderPlan(String mtrilOrderPlanCode);

	// 발주서 조회
	List<MaterialOrderDTO> listOrder(Map<String, Object> map);
	List<MaterialOrderDTO> infoOrder(String mtrilOrderCode);
}
