package com.ezotex.supply.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public List<ProductDTO> listProductBom() {
		return mapper.listProductBom();
	}
	
}
