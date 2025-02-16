package com.ezotex.standard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.standard.mappers.ProductMapper;
import com.ezotex.standard.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductMapper mapper;

	@Override
	public String[] listLclas(String productType) {
		return mapper.listLclas(productType);
	}

	@Override
	public String[] listSclasByLclas(String lclas) {
		return mapper.listSclasByLclas(lclas);
	}
	

}
