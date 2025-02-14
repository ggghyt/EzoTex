package com.ezotex.standard.mappers;

import java.util.List;

import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.PositionDTO;

public interface StandardMapper {
	// id 중복 확인
	int searchId(String id);
	
	// dept 리스트
	List<DeptDTO> deptList();
	
	// position 리스트
	List<PositionDTO> positionList();
}
