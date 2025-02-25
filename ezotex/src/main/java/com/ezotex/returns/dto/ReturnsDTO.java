package com.ezotex.returns.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

// 반품 등록 헤더
public class ReturnsDTO {
	private String returnCode;
	private String deliveryCode;
	private String companyCode;
	private String companyName;
	private String requestor;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date requestDate;
}
