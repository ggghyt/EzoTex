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
	@GetMapping("List")
	public String deliveryList() {
		return "delivery/DeliveryList";
	}
	
	@GetMapping("report")
	 public void report(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = datasource.getConnection();

	    // 레포트 파일 + 데이터(connection) => 완성
	    InputStream jasperStream = getClass().getResourceAsStream("/reports/test.jasper");
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, null, conn);
	    conn.close();

	    // Content-Type 설정 (필수)
	    response.setContentType("application/pdf");
	    
	    // PDF 출력
	    JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	 }
	
	@GetMapping(value="pdfView")
	public ModelAndView pdfview() throws Exception {
		//pdfView라는 뷰에 등록된 컴포넌트를 찾아감
		//filename이라는 키, "/reports/test.jasper"라는 값을 가진 맵을 생성
		//return new ModelAndView("pdfView", "filename", "/reports/test.jasper");
		String deliveryCode = "DEV2502240001";	//파라미터 받아왔다고 가정
		
		 // 사용자 입력 값을 포함하는 맵 생성
	    Map<String, Object> param = new HashMap<>();
	    param.put("deliveryCode", deliveryCode);
		param.put(JRParameter.REPORT_LOCALE, Locale.KOREA);
		param.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.FALSE);
		
	    // ModelAndView 생성
	    ModelAndView mav = new ModelAndView("pdfView");
	    mav.addObject("filename", "/reports/test.jasper"); // 리포트 파일 경로
	    mav.addObject("param", param); // 파라미터 추가
	
	    return mav;
	}
	
	@GetMapping(value="pdfViewDown")
	public ModelAndView pdfviewDown() throws Exception {
		String deliveryCode = "DEV2502240001";
		
		 // 사용자 입력 값을 포함하는 맵 생성
	    Map<String, Object> param = new HashMap<>();
	    param.put("deliveryCode", deliveryCode);
	
	    // ModelAndView 생성
	    ModelAndView mav = new ModelAndView("pdfViewDown");
	    mav.addObject("filename", "/reports/test.jasper"); // 리포트 파일 경로
	    mav.addObject("param", param); // 파라미터 추가
	
	    return mav;
	}
}
