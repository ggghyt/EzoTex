package com.ezotex.standard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SafetyStockDTO {
	private String safetyStockMonth;
	private String productCode;
	private int qy;
}
