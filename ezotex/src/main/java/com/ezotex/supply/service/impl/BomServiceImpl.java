package com.ezotex.supply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ezotex.comm.dto.SearchDTO;
import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.BomDTO;
import com.ezotex.supply.mappers.BomMapper;
import com.ezotex.supply.service.BomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BomServiceImpl implements BomService {
	
	private final BomMapper mapper;
	
	@Override
	public List<BomDTO> listBom() {
		return mapper.listBom();
	}

	@Override
	public BomDTO infoBom(String bomCode) {
		return mapper.infoBom(bomCode);
	}

	@Override
	public List<Map<String, Object>> listBomProduct(SearchDTO search) {
		return mapper.listBomProduct(search);
	}

	@Override
	public int countProduct(SearchDTO search) {
		return mapper.countProduct(search);
	}
	
}
