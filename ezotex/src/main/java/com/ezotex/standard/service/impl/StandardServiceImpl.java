package com.ezotex.standard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.standard.dto.EmpLoginInfoDTO;
import com.ezotex.standard.mappers.StandardMapper;
import com.ezotex.standard.service.StandardService;

@Service
public class StandardServiceImpl implements StandardService {

	@Autowired
	StandardMapper mapper;
	
	@Override
	public EmpLoginInfoDTO EmpLoginInfo(String emp_id) {
		return mapper.EmpLoginInfo(emp_id);
	}

}
