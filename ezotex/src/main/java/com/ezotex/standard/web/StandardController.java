package com.ezotex.standard.web;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezotex.comm.GridUtil;
import com.ezotex.standard.dto.CalenderDTO;
import com.ezotex.standard.dto.ProductCategoryDTO;
import com.ezotex.standard.dto.ProductListInfoDTO;
import com.ezotex.standard.dto.ProductOptionDTO;
import com.ezotex.standard.dto.SafetyStockDTO;
import com.ezotex.standard.dto.StorageDTO;
import com.ezotex.standard.dto.StorageProductDTO;
import com.ezotex.standard.service.impl.StandardServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/standard/*")
public class StandardController {

	final StandardServiceImpl service;
	
	// 제품 조회 페이지
	@GetMapping("/productInfo")
	public String productInfo() {
		return "standard/productInfo";
	}
	
	// 제품 전체 조회
	@ResponseBody
	@GetMapping("/productListAll")
	public Map<String, Object> productListAll() {
		ProductListInfoDTO productListInfoDTO = new ProductListInfoDTO();
		return GridUtil.grid(1, service.getCount(productListInfoDTO), service.productListAll(productListInfoDTO));
	}
	
	// 제품 선택 조회
	@ResponseBody
	@GetMapping("/productListSearch")
	public Map<String, Object> productListSearch(@RequestParam(name="productCode") String productCode, 
			                                     @RequestParam(name="productName") String productName, 
			                                     @RequestParam(name="productType") String productType, 
			                                     @RequestParam(name="lclas") String lclas, 
			                                     @RequestParam(name="sclas") String sclas, 
			                                     @RequestParam(name="minPrice") String minPrice, 
			                                     @RequestParam(name="maxPrice") String maxPrice) {
		ProductListInfoDTO productListInfoDTO = new ProductListInfoDTO();
		productListInfoDTO.setProductCode(productCode);
		productListInfoDTO.setProductName(productName);
		productListInfoDTO.setProductType(productType);
		productListInfoDTO.setLclas(lclas);
		productListInfoDTO.setSclas(sclas);
		int num;
		if (minPrice == "") {
			num = 0; 
		} else {
			num = Integer.parseInt(minPrice);
		}
		productListInfoDTO.setMinPrice(num);
		if (maxPrice == "") {
			num = 999999999;
		} else {
			num = Integer.parseInt(maxPrice);
		}
		productListInfoDTO.setMaxPrice(num);
		return GridUtil.grid(1, service.getCount(productListInfoDTO), service.productListAll(productListInfoDTO));
	}
	
	// 카테고리 반환
	@ResponseBody
	@GetMapping("/categoryLclas")
	public List<ProductCategoryDTO> categoryLclas() {
		return service.categoryLclas();
	}
	
	@ResponseBody
	@GetMapping("/categorySclas")
	public List<ProductCategoryDTO> categorySclas(@RequestParam(name="lclas") String lclas) {
		return service.categorySclas(lclas);
	}
	
	// 제품 단위 반환
	@ResponseBody
	@GetMapping("/unitName")
	public List<ProductListInfoDTO> unitName() {
		return service.unitNameList();
	}
	
	@Value("${file_img}")
	String file_img;
	
	// 제품 등록, 업데이트
	@PostMapping("/productInsert")
	public String productInsert(ProductListInfoDTO productListInfoDTO, RedirectAttributes attr) throws Exception {
		MultipartFile file = productListInfoDTO.getImgFile();
		UUID uuid = UUID.randomUUID();
		if (file.getOriginalFilename().length() > 3) {
			String uuidFileName = uuid + "_" + file.getOriginalFilename();
			file.transferTo(new File(file_img + uuidFileName));
			productListInfoDTO.setImg(uuidFileName);
		}
		
		service.ProductInfoInsert(productListInfoDTO);
		return "redirect:productInfo";
	}
	
	// 제품 카테고리 조회
	@ResponseBody
	@GetMapping("/productCategory")
	public List<ProductCategoryDTO> productCategory(@RequestParam(name="productCode") String productCode) {
		return service.productCategory(productCode);
	}
	
	// 카테고리 전송
	@ResponseBody
	@PostMapping("/categorySubmit")
	public void categorySubmit(@RequestBody List<ProductCategoryDTO> ProductCategoryList) {
		service.productCategoryInsert(ProductCategoryList);		
	}
	/*
	// 기업 검색
	@ResponseBody
	@GetMapping("/searchCom")
	public List<CompanyDTO> searchCom(@RequestParam(name="companyName") String companyName) {
		return service.searchCom(companyName);
	}
	*/
	// 옵션 사이즈 리스트
	@ResponseBody
	@GetMapping("/optionSizeList")
	public List<ProductOptionDTO> optionSizeList() {
		return service.optionSizeList();
	}
	
	// 옵션 가져오기
	@ResponseBody
	@GetMapping("/productOption")
	public List<ProductOptionDTO> productOption(@RequestParam(name="productCode") String productCode) {
		return service.productOption(productCode);
	}
	
	// 옵션 저장하기
	@ResponseBody
	@PostMapping("/optionSubmit")
	public void optionSubmit(@RequestBody List<ProductOptionDTO> productOptionDTO) {
		service.UpdateOption(productOptionDTO);
	}
	
	// 창고 조회 페이지
	@GetMapping("/storageInfo")
	public String storageInfo() {
		return "standard/storageInfo";
	}
	
	@ResponseBody
	@GetMapping("/storageList")
	public List<StorageDTO> storageList() {
		return service.storageList();
	}
	
	@ResponseBody
	@GetMapping("/storageInfoList")
	public List<StorageDTO> storageInfoList(@RequestParam(name="storageCode") String storageCode) {
		return service.storageInfoList(storageCode);
	}
	
	@ResponseBody
	@GetMapping("/storageVl")
	public int storageVl(@RequestParam(name="storageInfoCode") String storageInfoCode) {
		return service.storageVl(storageInfoCode);
	}
	
	@ResponseBody
	@GetMapping("/insertStorage")
	public void insertStorage(@RequestParam(name="storageName") String storageName) {
		service.insertStorage(storageName);
	}
	
	@ResponseBody
	@GetMapping("/updateStorage")
	public void updateStorage(@RequestParam(name="storageCode") String storageCode, 
			                  @RequestParam(name="storageName") String storageName, 
						      @RequestParam(name="maxRow") int maxRow, 
			                  @RequestParam(name="maxCol") int maxCol) {
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setStorageCode(storageCode);
		storageDTO.setStorageName(storageName);
		storageDTO.setMaxRow(maxRow);
		storageDTO.setMaxCol(maxCol);
		service.updateStorage(storageDTO);
	}
	
	@ResponseBody
	@GetMapping("/insertStorageInfo")
	public void insertStorageInfo(@RequestParam(name="storageCode") String storageCode, 
                                  @RequestParam(name="selectRow") int selectRow, 
		                          @RequestParam(name="selectCol") int selectCol, 
                                  @RequestParam(name="storageInfoName") String storageInfoName, 
                                  @RequestParam(name="vl") String vl) {
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setStorageCode(storageCode);
		storageDTO.setSelectRow(selectRow);
		storageDTO.setSelectCol(selectCol);
		storageDTO.setStorageInfoName(storageInfoName);
		storageDTO.setVl(vl);
		service.insertStorageInfo(storageDTO);
	}
	
	@ResponseBody
	@GetMapping("/updateStorageInfo")
	public void updateStorageInfo(@RequestParam(name="storageCode") String storageCode, 
                                  @RequestParam(name="selectRow") int selectRow, 
		                          @RequestParam(name="selectCol") int selectCol, 
                                  @RequestParam(name="storageInfoName") String storageInfoName, 
                                  @RequestParam(name="vl") String vl) {
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setStorageCode(storageCode);
		storageDTO.setSelectRow(selectRow);
		storageDTO.setSelectCol(selectCol);
		storageDTO.setStorageInfoName(storageInfoName);
		storageDTO.setVl(vl);
		service.updateStorageInfo(storageDTO);
	}
	
	@ResponseBody
	@GetMapping("/StorageProductList")
	public List<StorageProductDTO> StorageProductList(@RequestParam(name="storageCode") String storageCode, 
                                                      @RequestParam(name="selectRow") int selectRow, 
                                                      @RequestParam(name="selectCol") int selectCol, 
                                                      @RequestParam(name="storageInfoName") String storageInfoName, 
                                                      @RequestParam(name="vl") String vl) {
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setStorageCode(storageCode);
		storageDTO.setSelectRow(selectRow);
		storageDTO.setSelectCol(selectCol);
		storageDTO.setStorageInfoName(storageInfoName);
		storageDTO.setVl(vl);
		return service.StorageProductList(storageDTO);
	}
	
	@GetMapping("/safetyStock")
	public String safetyStock() {
		return "standard/safetyStock";
	}
	
	@ResponseBody
	@GetMapping("/safetyStockList")
	public List<SafetyStockDTO> safetyStockList(@RequestParam(name="productCode") String productCode) {
		return service.SafetyStockList(productCode);
	}
	
	@ResponseBody
	@GetMapping("/updateSafety")
	public void updateSafety(@RequestParam(name="safetyStockMonth") String safetyStockMonth, 
			                 @RequestParam(name="productCode") String productCode, 
			                 @RequestParam(name="qy") int qy) {
		SafetyStockDTO safetyStockDTO = new SafetyStockDTO();
		safetyStockDTO.setSafetyStockMonth(safetyStockMonth);
		safetyStockDTO.setProductCode(productCode);
		safetyStockDTO.setQy(qy);
		service.updateSafety(safetyStockDTO);
	}
	
	@ResponseBody
	@GetMapping("/updateStorageCol")
	public void updateStorageCol(@RequestParam(name="num") int num, 
                                 @RequestParam(name="storageCode") String storageCode, 
                                 @RequestParam(name="selectCol") int selectCol) {
		service.storageInfoColUpdate(num, storageCode, selectCol);
	}
	
	@ResponseBody
	@GetMapping("/updateStorageRow")
	public void updateStorageRow(@RequestParam(name="num") int num, 
                                 @RequestParam(name="storageCode") String storageCode, 
                                 @RequestParam(name="selectRow") int selectRow) {
		service.storageInfoRowUpdate(num, storageCode, selectRow);
	}
	
	@ResponseBody
	@GetMapping("/calendarInfo")
	public List<CalenderDTO> calendarInfo() {
		return service.calendarInfo();
	}
	
	@ResponseBody
	@GetMapping("/calendarCount")
	public List<CalenderDTO> calendarCount() {
		return service.calendarCount();
	}
	
	@ResponseBody
	@GetMapping("/mainProductImg")
	public List<ProductListInfoDTO> mainProductImg() {
		return service.mainProductImg();
	}
	
	@ResponseBody
	@GetMapping("/mainTodayCount")
	public Map<String, BigDecimal> mainTodayCount() {
		return service.mainTodayCount();	
	}
}
