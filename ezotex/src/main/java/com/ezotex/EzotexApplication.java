package com.ezotex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
@SpringBootApplication
@MapperScan(basePackages="com.ezotex.**.mappers")
public class EzotexApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzotexApplication.class, args);
	}

}
