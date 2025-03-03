package com.ezotex.store.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezotex.store.service.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRParameter;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/store/*")
public class InventoryController {

	private final StoreService service;
	
	@GetMapping("test")
	public String test() {
		return "store/test";
	}

	/**
	 * ========================================= 
	 * 입고관련 컨트롤
	 * =========================================
	 */

	// 제품 입고 등록 페이지
	@GetMapping("insertStore")
	public String storeInsert() {
		return "store/insertStore";
	}
	
	@GetMapping("materialInsert")
	public String materialInsert() {
		return "store/materialInsert";
	}
	

	// 반품 입고 등록 페이지
	@GetMapping("insertReturnStore")
	public String storeList() {
		return "store/insertReturnStore";
	}

	// 입고 문서 관리 페이지
	@GetMapping("docManagement")
	public String docManagement() {
		return "store/docManagement";
	}

	/**
	 * ========================================= 
	 * 자재관련 컨트롤
	 * =========================================
	 */

	// 재고 조회 페이지
	@GetMapping("productList")
	public String productList() {
		return "store/productList";
	}
	
	// 재고 조정 페이지
	@GetMapping("productManagement")
	public String productManagement() {
		return "store/productManagement";
	}
		
	// 실시간 재고 모니터링 페이지
	@GetMapping("monitoring")
	public String monitoring() {
		return "store/monitoring";
	}
		
	// 불량처리 조회 페이지
	@GetMapping("errorList")
	public String errorList() {
		return "store/errorList";
	}
	
	//제조업체 pdf뷰
		@GetMapping(value="pdfView")
		public ModelAndView pdfview(@RequestParam(name = "deliveryCode")String deliveryCode) throws Exception {
			//pdfView라는 뷰에 등록된 컴포넌트를 찾아감;
			
			// 사용자 입력 값을 포함하는 맵 생성
		    Map<String, Object> param = new HashMap<>();
		    param.put("P_DELIVERY_CODE", deliveryCode);
			param.put(JRParameter.REPORT_LOCALE, Locale.KOREA);
			param.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.FALSE);
			
		    // ModelAndView 생성
		    ModelAndView mav = new ModelAndView("pdfView");
		    mav.addObject("filename", "/reports/delivery/deliveryCard.jasper"); // 리포트 파일 경로
		    mav.addObject("param", param); // 파라미터 추가
		
		    return mav;
		}
		
		
	// 제스퍼 테스트중
	@GetMapping(value="ErrorPdfview")
	public ModelAndView testPdf() throws Exception{
		ModelAndView mav = new ModelAndView("pdfView");
		mav.addObject("filenam", "/reports/ErrorListTest.jasper");
		
		return mav;
	}
	
	

}
