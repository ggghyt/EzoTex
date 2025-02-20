package com.ezotex;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.dto.SupplyDTO;
import com.ezotex.supply.mappers.SupplyMapper;
import com.ezotex.supply.service.impl.SupplyServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class EzotexApplicationTests {

	@Autowired
	SupplyServiceImpl service;
	@Autowired
	SupplyMapper mapper;
	
	@Test
	void contextLoads() {
		SupplyDTO dto = new SupplyDTO();
		dto.setProductCode("PRD0001");
		List<ProductDTO> sizeList = mapper.findSize(dto.getProductCode());
		List<Map<String, Object>> list = mapper.pivotProductSupply(dto, sizeList);
		List<Map<String, Object>> compareList = mapper.pivotProductOption(dto.getProductCode(), sizeList);
		
		log.info(list.toString());
		log.info(compareList.get(0).toString()); // forEach
		
		Set<String> sizeSet = compareList.get(0).keySet(); // 해당 제품의 사이즈명만 Set으로 반환
		//sizeSet.remove("PRODUCT_COLOR");
		log.info("set:::", sizeSet.toString());
		
		list.forEach(map -> {
			compareList.forEach( comp -> {
				String color = (String) map.get("PRODUCT_COLOR");
				String CompColor = (String) comp.get("PRODUCT_COLOR");
				
				if(color.equals(CompColor)) {
					for(String size : sizeSet) {
						if(comp.get(size).equals(0)) map.replace(size, 'X');
					}
				}
			});
		});
		
		log.info("list:::", list.toString());
	}


}
