package com.ezotex.supply.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.supply.dto.MaterialOrderDTO;
import com.ezotex.supply.dto.MaterialOrderPlanDTO;
import com.ezotex.supply.service.impl.MaterialOrderServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mtr/*")
public class MaterialOrderRestController {
		
	private final MaterialOrderServiceImpl orderService;
		
	/******************** 발주서 ********************/	
	// 발주할 자재 목록
	@GetMapping("materialList")
	public Map<String, Object> materialList(@RequestParam Map<String, String> params) { // 검색 조건 파라미터
		log.info("params::", params.toString());
		int totalCnt = orderService.countProductByCompany(params);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, orderService.listProductByCompany(params));
		log.info(map.toString());
		return map;
	}
	
	// 업체 목록
	@GetMapping("companyList")
	public Map<String, Object> companyList(@RequestParam Map<String, String> params) { // 검색 조건 파라미터
		log.info("params::", params.get("productCode"));
		int totalCnt = orderService.countCompanyByProduct(params);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, orderService.listCompanyByProduct(params));
		log.info(map.toString());
		return map;
	}
	
	// 업체/자재의 옵션별 상세정보
	@GetMapping("optionPriceList")
	public List<Map<String, Object>> optionPriceList(@RequestParam("productCode") String productCode, 
										  		@RequestParam("companyCode")String companyCode) {
		List<Map<String, Object>> list = orderService.listColorInfoByCompany(productCode, companyCode);
		log.info(list.toString());
		return list;
	}
	
	// 업체/자재의 상세정보 (단일제품)
	@GetMapping("optionPrice")
	public Map<String, Object> optionPrice(@RequestParam("productCode") String productCode, 
										  		@RequestParam("companyCode")String companyCode) {
		Map<String, Object> result = orderService.infoMtrByCompany(productCode, companyCode);
		log.info(result.toString());
		return result;
	}
	
	// 발주서 등록
	@PostMapping("mtrOrder")
	public Boolean insertMaterialOrder(@RequestBody Map<String, Object> orderList) {
		log.info("orderList::: " + orderList.toString());
		return orderService.insertMaterialOrder(orderList); // true/false 반환
	}
	
	// 발주계획서 등록
	@PostMapping("mtrOrderPlan")
	public Boolean insertMaterialOrderPlan(@RequestBody Map<String, Object> orderPlanList) {
		return orderService.insertMaterialOrderPlan(orderPlanList); // true/false 반환
	}
	
	// 발주서 수정
	@PutMapping("mtrOrder")
	public Boolean updateMaterialOrderStatus(@RequestBody Map<String,Object> orderDetail) {
		return orderService.updateOrder(orderDetail); // true/false 반환
	}
	
	// 발주계획서 수정
	@PutMapping("mtrOrderPlan")
	public Boolean updateMaterialOrderPlan(@RequestBody Map<String,Object> orderPlanDetail) {
		return orderService.updatePlan(orderPlanDetail); // true/false 반환
	}
	
	// 발주계획서 목록 
	@GetMapping("orderPlanList")
	public Map<String, Object> orderPlanList(@RequestParam Map<String, Object> params, // 검색 조건 파라미터
										@RequestParam(value="status", required=false) String[] status) { // List타입은 2개 이상 값이 필수라 배열로 받음.
		params.put("status", status);
		int totalCnt = orderService.countOrderPlan(params);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, orderService.listOrderPlan(params));
		log.info(map.toString());
		return map;
	}
	
	// 발주계획서 단건조회
	@GetMapping("orderPlan/{code}")
	public List<MaterialOrderPlanDTO> orderPlan(@PathVariable String code) {
		List<MaterialOrderPlanDTO> result = orderService.infoOrderPlan(code);
		log.info(result.toString());
		return result;
	}
	
	// 발주서 목록 
	@GetMapping("orderList")
	public Map<String, Object> orderList(@RequestParam Map<String, Object> params, // 검색 조건 파라미터
										@RequestParam(value="status", required=false) String[] status) { // List타입은 2개 이상 값이 필수라 배열로 받음.
		params.put("status", status);
		List<MaterialOrderDTO> result = orderService.listOrder(params);
		Map<String, Object> map = GridUtil.grid(1, result.size(), result);
		log.info(map.toString());
		return map;
	}
	
	// 발주서 단건조회
	@GetMapping("orderInfo/{code}")
	public List<MaterialOrderDTO> orderInfo(@PathVariable String code) {
		List<MaterialOrderDTO> result = orderService.infoOrder(code);
		log.info(result.toString());
		return result;
	}
}
