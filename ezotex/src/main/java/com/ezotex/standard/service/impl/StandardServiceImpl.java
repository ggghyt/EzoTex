package com.ezotex.standard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.standard.dto.AddressListDTO;
import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.DivyAddressDTO;
import com.ezotex.standard.dto.EmpDTO;
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

	@Override
	@Transactional
	public int insertEmp(EmpDTO empDTO, AddressListDTO addDTO) {
		int add_result = mapper.insertAddress(addDTO);
		String address_seq = mapper.findAddressSeq();
		empDTO.setAddressSeq(address_seq);
		int emp_result = mapper.insertEmp(empDTO);
		return (emp_result == 1 && add_result == 1) ? 1 : 0;
	}

	@Override
	public int idApproval(String id) {
		return mapper.idApproval(id);
	}

	@Override
	@Transactional
	public int insertCompany(CompanyDTO companyDTO, AddressListDTO addDTO) {
		String add_arr[] = companyDTO.getAddressMain().split(" ");
		DivyAddressDTO divyAddressDTO = new DivyAddressDTO();
		divyAddressDTO.setMain(add_arr[0]);
		divyAddressDTO.setSub(add_arr[1]);
		String addressCode = mapper.findDivyAddress(divyAddressDTO);
		companyDTO.setAddressCode(addressCode);
		System.out.println(companyDTO.toString());
		
		int add_result = mapper.insertAddress(addDTO);
		String address_seq = mapper.findAddressSeq();
		companyDTO.setAddressSeq(address_seq);
		int company_result = mapper.insertCompany(companyDTO);
		return (company_result == 1 && add_result == 1) ? 1 : 0;
	}
	
}
