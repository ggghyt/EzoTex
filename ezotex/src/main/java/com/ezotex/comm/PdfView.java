package com.ezotex.comm;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class PdfView extends AbstractView {
	
	@Autowired
	DataSource datasource;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		//DB커넥션 가져오기
		Connection conn = datasource.getConnection();
		//파일명 가져오기
		String reportFile = (String) model.get("filename");
		//사용자의 요청에 따라 달라지는 파라미터 가져오기
		HashMap<String, Object> map = (HashMap<String, Object>) model.get("param");
		//파일 읽기(.jasper파일을 인풋스트림으로 읽음)
		InputStream jasperStream = getClass().getResourceAsStream(reportFile);
		//읽은 내용을 바탕으로 jasperReport객체 로드
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		//파라미터를 넣어서 검색한 결과를 변수에 담음(000.jrxml로드 -> map변수에 저장한 사용자의 요청에 담긴 파라미터를 동적으로 쿼리문에 바인딩--> .jrxml파일에 설정해줘야함.)
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
		//커넥션 닫기
		conn.close();
		
		// Content-Type 설정 (필수)
	    response.setContentType("application/pdf");
		
	    //pdf화면에 출력
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}
}


