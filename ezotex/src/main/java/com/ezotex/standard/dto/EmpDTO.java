package com.ezotex.standard.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpDTO {
	private String empCode;
	private String empId;
	private String empPassword;
	private String empName;
	private String empPhone;
	private String empEmail;
	private String deptCode;
	private String positionCode;
	private String empImg;
	private MultipartFile empImgFile;
	private Date hireDate;
	private Date fireDate;
	private String approval;
	
	private String addressSeq;
	private String addressNumber;
	private String addressMain;
	private String addressInfo;
	private String addressReference;
}
