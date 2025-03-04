package com.ezotex.standard.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InfomationDTO {
	private int infoNo;
	private String title;
	private String content;
	private String writer;
	private String writerName;
	private Date writeDate;
	private Date editDate;
	
}
