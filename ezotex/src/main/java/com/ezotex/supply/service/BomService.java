package com.ezotex.supply.service;

import java.util.List;

import com.ezotex.supply.dto.BomDTO;

public interface BomService {
	List<BomDTO> listBom();
	BomDTO infoBom(String bomCode);
}
