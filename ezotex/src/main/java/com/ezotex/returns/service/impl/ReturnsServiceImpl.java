package com.ezotex.returns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;
import com.ezotex.returns.dto.ReturnsDTO;
import com.ezotex.returns.dto.ReturnsProductDTO;
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
		System.out.println("매퍼출력"+mapper.getDeliProduct(deliveryCode));
		return mapper.getDeliProduct(deliveryCode);
	}

	@Override
	public ReturnsDTO insertReturn(ReturnsDTO returnData) {
		System.out.println(returnData);
		mapper.insertReturn(returnData);
		return returnData;
	}
	
	@Override
	public List<ReturnsProductDTO> insertProductReturn(List<ReturnsProductDTO> returnProductData) {
		System.out.println("대체 왜 등록이 안되냐"+returnProductData);
		mapper.insertProductReturn(returnProductData);
		return returnProductData;
	}

}
