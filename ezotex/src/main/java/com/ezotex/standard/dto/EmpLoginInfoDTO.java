package com.ezotex.standard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpLoginInfoDTO {
	private String empCode;
	private String empId;
	private String empPassword;
	private String empName;
	private String empEmail;
	private int deptNo;
	private int positionNo;
	private String empImg;
}
