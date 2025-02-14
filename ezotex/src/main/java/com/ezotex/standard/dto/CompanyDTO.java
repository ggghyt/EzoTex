package com.ezotex.standard.dto;

import org.springframework.web.multipart.MultipartFile;

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
	private String companyPhone;
	private String companyEmail;
	private String companyName;
	private String addressSeq;
	private String companyNumber;
	private String companyImg;
	private MultipartFile ecompanyImgFile;
	private String section;
	private int distance;
	private int requireTime;
	private String addressCode;
}
