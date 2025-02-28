package com.ezotex.supply.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.MaterialOrderPlanDTO;
import com.ezotex.supply.service.impl.BomServiceImpl;
import com.ezotex.supply.service.impl.MaterialOrderServiceImpl;
import com.ezotex.supply.service.impl.SupplyServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/supply/*")
public class SupplyRestController {
	
	private final BomServiceImpl bomService;
	private final SupplyServiceImpl service;
	private final MaterialOrderServiceImpl orderService;
		
	/******************** 자재명세서 ********************/	
	// bom 제품 목록
	@GetMapping("bomProductList")
	public Map<String, Object> bomProductList(ProductDTO product) { // 검색 조건 파라미터
		log.info(product.toString());
		int totalCnt = bomService.countProduct(product);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, bomService.listBomProduct(product));
		log.info(map.toString());
		return map;
	}
	
	// 해당 제품의 색상 목록
	@GetMapping("options/{prdCode}")
	public List<ProductDTO> sizeList(@PathVariable String prdCode) {
		List<ProductDTO> list = bomService.listColor(prdCode);
		return list;
	}

	// 색상에 따른 사이즈 옵션 목록
	@GetMapping("options/{prdCode}/{color}")
	public List<ProductDTO> colorList(@PathVariable String prdCode, @PathVariable String color) {
		List<ProductDTO> list = bomService.listSizeByColor(prdCode, color);
		return list;
	}
	
	// bom 자재 목록
	@GetMapping("bomMaterialList")
	public Map<String, Object> bomMaterialList(ProductDTO product) { // 검색 조건 파라미터
		log.info(product.toString());
		Map<String, Object> map = GridUtil.grid(0, 0, bomService.listBomMaterial(product)); // 페이지네이션 X
		log.info(map.toString());
		return map;
	}
	
	// bom 등록
	@PostMapping("bom")
	public Boolean insertBom(@RequestBody Map<String, Object> bomList) {
		log.info("bom::: " + bomList.toString());
		return bomService.insertBom(bomList); // true/false 반환
	}
	
	
	/******************** 공급계획서 ********************/	
	// 단순 제품 목록
	@GetMapping("productList")
	public Map<String, Object> productList(ProductDTO product) { // 검색 조건 파라미터
		log.info(product.toString());
		int totalCnt = bomService.countProduct(product);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, service.listProduct(product));
		log.info(map.toString());
		return map;
	}
	
	// 해당 제품의 색상 and 사이즈 집계
	@GetMapping("optionPivot/{productCode}")
	public Map<String, Object> optionPivot(@PathVariable String productCode) { // 검색 조건 파라미터
		Map<String, Object> map = GridUtil.grid(0, 0, service.pivotProductOption(productCode));
		return map;
	}
	
	// 단일제품이나 단일옵션(색상/사이즈)인 경우 단순 목록 반환
	@GetMapping("optionList/{productCode}")
	public Map<String, Object> optionList(@PathVariable String productCode) { // 검색 조건 파라미터
		Map<String, Object> map = GridUtil.grid(0, 0, service.findOptions(productCode));
		return map;
	}
	
	// 공급계획서 목록
	@GetMapping("supplyPlanList")
	public Map<String, Object> supplyPlanList(@RequestParam Map<String, Object> params, // 검색 조건 파라미터
										@RequestParam(value="season", required=false) String[] season) { // List타입은 2개 이상 값이 필수라 배열로 받음.
		params.put("season", season); // checkbox 선택값 배열
		int totalCnt = service.countSupplyPlan(params);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, service.listSupplyPlan(params));
		return map;
	}
	
	// 공급계획서 상세조회
	@GetMapping("supplyPlan/{planCode}")
	public Map<String, Object> supplyPlanPivot(@PathVariable String planCode) {
		return GridUtil.grid(0, 0, service.infoSupplyPlan(planCode));
	}
	
	// 공급계획서에 등록된 제품의 옵션 집계
	@GetMapping("supplyPlanPivot")
	public Map<String, Object> supplyPlanPivot(@RequestParam Map<String, Object> params) {
		return GridUtil.grid(0, 0, service.pivotProductSupply(params));
	}
	
	// 공급계획서 등록
	@PostMapping("plan")
	public Boolean insertSupplyPlan(@RequestBody Map<String, Object> supplyList) {
		log.info("planData::: " + supplyList.toString());
		return service.insertSupplyPlan(supplyList); // true/false 반환
	}
	
	
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
	
	// 업체/자재별 옵션 및 단가 목록
	@GetMapping("optionPriceList")
	public List<Map<String, Object>> optionPriceList(@RequestParam("productCode") String productCode, 
										  		@RequestParam("companyCode")String companyCode) { // 검색 조건 파라미터
		List<Map<String, Object>> list = orderService.listColorByCompany(productCode, companyCode);
		log.info(list.toString());
		return list;
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
		log.info("orderPlanList::: " + orderPlanList.toString());
		return orderService.insertMaterialOrderPlan(orderPlanList); // true/false 반환
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
}
