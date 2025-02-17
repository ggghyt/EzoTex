package com.ezotex.standard.service;

import java.util.List;

import com.ezotex.standard.dto.AddressListDTO;
import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.EmpDTO;
import com.ezotex.standard.dto.PositionDTO;

public interface StandardService {
	int searchId(String id);
	List<DeptDTO> deptList();
	List<PositionDTO> positionList();
	int insertEmp(EmpDTO empDTO, AddressListDTO addDTO);
	int idApproval(String id);
}
