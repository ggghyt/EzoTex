package com.ezotex.standard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DivyAddressDTO {
	private int addressCode;
	private String main;
	private String sub;
}
