package com.ezotex.returns.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
// 반품 등록 제품
public class ReturnsProductDTO {
	private String returnProductNo;
	private String returnCode;
	private String productCode;
	private String productName;
	private String productColor;
	private String productSize;
	private String faultyReason;
	private int qy;
	private String requestor;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date requestDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date returnDate;
	private String processingStatus;
	private int unitPrice;
	private String show;
}
