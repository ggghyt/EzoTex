package com.ezotex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ezotex.supply.service.impl.SupplyServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class EzotexApplicationTests {

	@Autowired
	SupplyServiceImpl service;
	
	@Test
	void contextLoads() {
	}


}
