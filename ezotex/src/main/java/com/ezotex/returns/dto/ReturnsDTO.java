package com.ezotex.returns.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReturnsDTO {
	private String return_code;
	private String dvyfg_code;
	private String company_code;
	private String company_name;
	private String requestor;
	private Date request_date;
	private String status;
}
