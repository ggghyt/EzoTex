package com.ezotex.supply.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.standard.dto.ProductDTO;
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
		
	// 내부 데이터 출력
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
	
	
	// 공급계획서
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
	
	// 해당 제품의 색상 or 사이즈 일괄 목록
	@GetMapping("optionList/{productCode}")
	public Map<String, Object> optionList(@PathVariable String productCode) { // 검색 조건 파라미터
		Map<String, Object> map = GridUtil.grid(0, 0, service.findOptions(productCode));
		return map;
	}
	
	// 공급계획서 등록
	@PostMapping("plan")
	public Boolean insertSupplyPlan(@RequestBody Map<String, Object> supplyList) {
		log.info("planData::: " + supplyList.toString());
		return service.insertSupplyPlan(supplyList); // true/false 반환
	}
	
	
	// 발주서
	// 발주할 자재 목록 조회
	@GetMapping("materialList")
	public Map<String, Object> materialList(Map<String, String> params) { // 검색 조건 파라미터
		log.info(params.toString());
		int totalCnt = orderService.countProductByCompany(params);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, orderService.listProductByCompany(params));
		log.info(map.toString());
		return map;
	}
	
	// 업체 목록 조회
	@GetMapping("companyList")
	public Map<String, Object> companyList(Map<String, String> params) { // 검색 조건 파라미터
		log.info(params.toString());
		int totalCnt = orderService.countCompanyByProduct(params);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, orderService.listCompanyByProduct(params));
		log.info(map.toString());
		return map;
	}
	
}
