package com.ezotex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class EzotexApplicationTests {

	@Autowired
//	SampleSupplyMapper mapper;
	
	@Test
	void contextLoads() {
//		log.info(mapper.infoBom().toString());
	}

}
