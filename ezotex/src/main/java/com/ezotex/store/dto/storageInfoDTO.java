package com.ezotex.store.dto;

import lombok.Data;

@Data
public class storageInfoDTO {

	private String storageInfoCode;
	private String storageCode;
	private int selectRow;
	private int selectCol;
	private String storageInfoName;
	private String vl;
	private int totalVlValue;
	
}
