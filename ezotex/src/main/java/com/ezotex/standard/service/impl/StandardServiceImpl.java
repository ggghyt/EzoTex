package com.ezotex.standard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.PositionDTO;
import com.ezotex.standard.mappers.StandardMapper;
import com.ezotex.standard.service.StandardService;

@Service
public class StandardServiceImpl implements StandardService {

	@Autowired
	StandardMapper mapper;

	@Override
	public int searchId(String id) {
		return mapper.searchId(id);
	}

	@Override
	public List<DeptDTO> deptList() {
		return mapper.deptList();
	}

	@Override
	public List<PositionDTO> positionList() {
		return mapper.positionList();
	}
	
	
}
