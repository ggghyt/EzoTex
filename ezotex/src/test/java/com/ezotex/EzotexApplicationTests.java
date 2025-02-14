package com.ezotex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ezotex.supply.mappers.BomMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class EzotexApplicationTests {

	@Autowired
	BomMapper mapper;
	
	@Test
	void contextLoads() {
	}


}
