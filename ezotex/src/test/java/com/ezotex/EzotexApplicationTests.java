package com.ezotex;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ezotex.supply.mappers.MaterialOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class EzotexApplicationTests {

	@Autowired
	MaterialOrderMapper mapper;
	
	@Test
	void contextLoads() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("companyCode", "COM0021");
		log.info(mapper.listProductByCompany(map).toString());
	}


}
