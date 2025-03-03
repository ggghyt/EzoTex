package com.ezotex.delivery.web;


import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/delivery/*")
public class DeliveryController {
	
	@Autowired
	DataSource datasource;
	
	
	//납품 관리 페이지(제조업체 사용)
	@GetMapping("DeliveryManagement")
	public String deliveryManagement() {
		
		return "delivery/DeliveryManagement";
	}
	
	//납품 관리 페이지(공급업체 사용)
	@GetMapping("SupplierManagement")
	public String SupplyManagement() {
		
		return "delivery/SupplierDeliveryManagement";
	}
	
	//납품 조회 페이지(제조업체)
	@GetMapping("deliveryList")
	public String deliveryList() {
		return "delivery/DeliveryList";
	}
	
	//납품 조회 페이지(공급업체)
	@GetMapping("SupplyList")
	public String SupplyList() {
		return "delivery/SupplierDeliveryList";
	}
	
	//납품 관리 페이지(제조업체 사용)
	@GetMapping("packingManagement")
	public String packingManagement() {
		
		return "delivery/packingManagement";
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
	
	//공급업체 pdf뷰
	@GetMapping(value="supplyPdfview")
	public ModelAndView supplyPdfview(@RequestParam(name = "deliveryCode")String deliveryCode) throws Exception {
		//pdfView라는 뷰에 등록된 컴포넌트를 찾아감;
		
		// 사용자 입력 값을 포함하는 맵 생성
		Map<String, Object> param = new HashMap<>();
		param.put("P_DELIVERY_CODE", deliveryCode);
		param.put(JRParameter.REPORT_LOCALE, Locale.KOREA);
		param.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.FALSE);
		
		// ModelAndView 생성
		ModelAndView mav = new ModelAndView("pdfView");
		mav.addObject("filename", "/reports/delivery/supplyDeliveryCard.jasper"); // 리포트 파일 경로
		mav.addObject("param", param); // 파라미터 추가
		
		return mav;
	}

	//배송기사
	@GetMapping("driveList")
	public String driveList() {
		
		return "delivery/driveList";
	}
}
