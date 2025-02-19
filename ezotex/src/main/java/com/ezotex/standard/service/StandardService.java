package com.ezotex.standard.service;

import java.util.List;

import com.ezotex.standard.dto.AddressListDTO;
import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.DivyAddressDTO;
import com.ezotex.standard.dto.EmpDTO;
import com.ezotex.standard.dto.PositionDTO;
import com.ezotex.standard.dto.ProductListInfoDTO;
import com.ezotex.standard.dto.ResetPasswordDTO;

public interface StandardService {
	int searchId(String id);
	List<DeptDTO> deptList();
	List<PositionDTO> positionList();
	int insertEmp(EmpDTO empDTO, AddressListDTO addDTO);
	int idApproval(String id);
	int insertCompany(CompanyDTO companyDTO, AddressListDTO addDTO);
	ResetPasswordDTO findNameEmail(String id);
	int passwordUpdate(String id, String password);
	List<ProductListInfoDTO> productList();
}
