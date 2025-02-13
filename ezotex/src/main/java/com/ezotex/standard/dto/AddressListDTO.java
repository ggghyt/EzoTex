package com.ezotex.standard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressListDTO {
	private String addressSeq;
	private String addressNumber;
	private String addressMain;
	private String addressInfo;
	private String addressReference;
}
