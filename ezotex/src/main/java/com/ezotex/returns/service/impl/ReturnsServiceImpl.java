package com.ezotex.returns.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.returns.dto.ChangeOrderDTO;
import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;
import com.ezotex.returns.dto.ReturnsDTO;
import com.ezotex.returns.dto.ReturnsProductDTO;
import com.ezotex.returns.dto.changeDTO;
import com.ezotex.returns.dto.showDTO;
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
		System.out.println("매퍼출력" + mapper.getDeliProduct(deliveryCode));
		return mapper.getDeliProduct(deliveryCode);
	}

	@Override
	public ReturnsDTO insertReturn(ReturnsDTO returnData) {
		System.out.println(returnData);
		mapper.insertReturn(returnData);
		return returnData;
	}

	@Override
	public boolean insertProductReturn(List<ReturnsProductDTO> returnProductData) {

		returnProductData.forEach(data -> {
			mapper.insertProductReturn(data);
		});

		return true;
	}

	// 교환 조회
	@Override
	public List<changeDTO> getChangeList() {
		return mapper.getChangeList();
	}

	// 교환 제품 조회
	public List<changeDTO> getChangeProductList(String returnCode) {
		return mapper.getChangeProductList(returnCode);
	}

	// 반품 전체 조회
	@Override
	public List<ReturnsDTO> getReturnList() {
		return mapper.getReturnList();
	}

	@Override
	public List<ReturnsProductDTO> getReturnProductList() {
		return mapper.getReturnProductList();
	}

	// 교환 주문 등록
	@Override
	public ChangeOrderDTO insertOrder(ChangeOrderDTO order) {
		System.out.println(order);
		mapper.insertOrder(order);
		return order;
	}

	// 교환 주문 제품 등록
	@Transactional
	@Override
	public boolean insertProductOrder(Map<String, Object> product) {

		String productOrderCode = (String) product.get("productOrderCode");

		List<ChangeOrderDTO> odto = (List<ChangeOrderDTO>) product.get("option");
		System.out.println("sdsd" + productOrderCode);
		System.out.println("dsds" + odto);
		odto.forEach(data -> {
			data.setProductOrderCode(productOrderCode);
			System.out.println("반복 확인" + data);
			mapper.insertProductOrder(data);
		});

		return true;
	}

	@Override
	public boolean showChange(List<showDTO> newReturnData) {
		newReturnData.forEach(data -> {
			mapper.showChange(data);
		});
		return true;
	}

	// 반품 제품 조회
	public List<ReturnsProductDTO> selectReturnProductList(String returnCode) {
		return mapper.selectReturnProductList(returnCode);
	}
	
	// 일별 제품별 조회
	@Override
	public List<ReturnsProductDTO> getTotalReturnProduct() {
		return mapper.getTotalReturnProduct();
	}

}
