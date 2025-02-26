package com.ezotex.returns.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class changeDTO {
	private String returnCode;
	private String deliveryCode;
	private String companyCode;
	private String companyName;
	private String requestor;
	private String companyCharger;
    private String companyPhone;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date requestDate;
	private String returnProductNo;
	private String productCode;
	private String productName;
	private String productColor;
	private String productSize;
	private String faultyReason;
	private int qy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date returnDate;
	private String processingStatus;
	private int unitPrice;
}
