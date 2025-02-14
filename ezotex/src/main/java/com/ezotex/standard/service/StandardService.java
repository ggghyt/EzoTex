package com.ezotex.standard.service;

import java.util.List;

import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.PositionDTO;

public interface StandardService {
	int searchId(String id);
	List<DeptDTO> deptList();
	List<PositionDTO> positionList();
}
