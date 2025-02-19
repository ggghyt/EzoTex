package com.ezotex.supply.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.mappers.SupplyMapper;
import com.ezotex.supply.service.SupplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplyServiceImpl implements SupplyService {
	private final SupplyMapper mapper;

	@Override
	public List<ProductDTO> listProduct(ProductDTO product) {
		return mapper.listProduct(product);
	}
	
	
}
