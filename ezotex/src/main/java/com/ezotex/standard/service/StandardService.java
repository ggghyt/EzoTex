package com.ezotex.standard.service;

import java.util.List;
import java.util.Map;

import com.ezotex.standard.dto.AddressListDTO;
import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.EmpDTO;
import com.ezotex.standard.dto.PositionDTO;
import com.ezotex.standard.dto.ProductCategoryDTO;
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
	List<Map<String, Object>> productListAll(ProductListInfoDTO productListInfoDTO);
	List<ProductCategoryDTO> categoryLclas();
	List<ProductCategoryDTO> categorySclas(String lclas);
	public int getCount(ProductListInfoDTO productListInfoDTO);
}
