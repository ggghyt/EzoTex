package com.ezotex.supply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ezotex.standard.dto.ProductDTO;
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
	public List<Map<String, Object>> listBomProduct(ProductDTO product) {
		return mapper.listBomProduct(product);
	}

	@Override
	public int countBomProduct(ProductDTO product) {
		return mapper.countBomProduct(product);
	}

	@Override
	public List<Map<String, Object>> listBomMaterial(ProductDTO product) {
		return mapper.listBomMaterial(product);
	}
	
}
