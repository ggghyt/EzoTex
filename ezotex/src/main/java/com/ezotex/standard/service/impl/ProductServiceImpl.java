package com.ezotex.standard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.standard.dto.ProductOptionDTO;
import com.ezotex.standard.mappers.ProductMapper;
import com.ezotex.standard.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductMapper mapper;

	@Override
	public List<ProductOptionDTO> listOption(String productCode) {
		return mapper.listOption(productCode);
	}

	@Override
	public String[] listLclas() {
		return mapper.listLclas();
	}

	@Override
	public String[] listSclasByLclas(String lclas) {
		return mapper.listSclasByLclas(lclas);
	}
	

}
