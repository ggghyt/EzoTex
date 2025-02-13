package com.ezotex.standard.dto;

import java.util.Date;

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
	private String addressSeq;
	private String deptCode;
	private String positionCode;
	private String empImg;
	private Date hireDate;
	private Date fireDate;
}
