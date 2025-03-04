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
import com.ezotex.supply.dto.SupplyDTO;
import com.ezotex.supply.service.impl.BomServiceImpl;
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
	
	// 해당 제품의 사이즈 목록
	@GetMapping("optionSize/{prdCode}")
	public List<ProductDTO> sizeList(@PathVariable String prdCode) {
		List<ProductDTO> list = bomService.listSize(prdCode);
		return list;
	}
	
	// 해당 제품의 색상 목록
	@GetMapping("options/{prdCode}")
	public List<ProductDTO> colorList(@PathVariable String prdCode) {
		List<ProductDTO> list = bomService.listColor(prdCode);
		return list;
	}

	// 색상에 따른 사이즈 옵션 목록 (색상/사이즈 모두 있는 경우)
	@GetMapping("options/{prdCode}/{color}")
	public List<ProductDTO> sizeByColorList(@PathVariable String prdCode, @PathVariable String color) {
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
	
	// bom 등록 이후 모든 옵션이 등록되었는지 확인
	@GetMapping("bomAllInserted/{prdCode}")
	public Boolean bomMaterialList(@PathVariable String prdCode) {
		return bomService.checkBomAllInserted(prdCode);
	}
	
	
	/******************** 공급계획서 ********************/	
	// 단순 제품 목록
	@GetMapping("productList")
	public Map<String, Object> productList(ProductDTO product) { // 검색 조건 파라미터
		int totalCnt = bomService.countProduct(product);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, service.listProduct(product));
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
	@GetMapping("supplyPlan")
	public Map<String, Object> supplyPlanPivot(@RequestParam(value="supplyPlanCode", required=false) String planCode,
			                                   @RequestParam(value="productCode", required=false) String productCode) {
		return GridUtil.grid(0, 0, service.infoSupplyPlan(planCode, productCode));
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
	
	// 자재소요계획 조회
	@GetMapping("listMrp")
	public Map<String, Object> listMrp(SupplyDTO dto) {
		return GridUtil.grid(0, 0, service.listMrp(dto));
	}
	
}
