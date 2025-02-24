package com.ezotex.returns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;
import com.ezotex.returns.mappers.ReturnsMapper;
import com.ezotex.returns.service.ReturnsService;

@Service
public class ReturnsServiceImpl implements ReturnsService {
	
	@Autowired
	ReturnsMapper mapper;
	
	@Override
	public List<DeliveryReturnsDTO> getDeliveryList() {
		return mapper.getDeliveryList();
	}

	@Override
	public List<DeliveryDetailsReturnsDTO> getDeliProduct(String deliveryCode) {
		System.out.println("래셯뫠롷"+mapper.getDeliProduct(deliveryCode));
		return mapper.getDeliProduct(deliveryCode);
	}

}
