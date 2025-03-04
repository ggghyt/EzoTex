package com.ezotex.standard.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyDTO {
	private String companyCode;
	private String companyId;
	private String companyPassword;
	private String companyExponent;
	private String companyCharger;
	
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 양식이 아닙니다")
	private String companyPhone;
	
	private String companyEmail;
	private String companyName;
	private String companyNumber;
	private String companyImg;
	private MultipartFile companyImgFile;
	private String section;
	private int distance;
	private Double requireTime;
	private String addressCode;
	private String approval;
	
	private String addressSeq;
	private String addressNumber;
	private String addressMain;
	private String addressInfo;
	private String addressReference;
}
