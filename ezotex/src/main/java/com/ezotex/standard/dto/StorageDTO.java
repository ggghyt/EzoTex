package com.ezotex.standard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StorageDTO {
	private String storageCode;
	private String storageName;
	private int maxRow;
	private int maxCol;
	
	private String storageInfoCode;
	private String storageInfoName;
	private int selectRow;
	private int selectCol;
	private String vl;
}
