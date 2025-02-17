package com.ezotex.standard.mappers;

import java.util.List;

import com.ezotex.standard.dto.AddressListDTO;
import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.EmpDTO;
import com.ezotex.standard.dto.PositionDTO;

public interface StandardMapper {
	// id 중복 확인
	int searchId(String id);
	
	// dept 리스트
	List<DeptDTO> deptList();
	
	// position 리스트
	List<PositionDTO> positionList();
	
	// emp 회원 가입
	int insertEmp(EmpDTO empDTO);
	
	// 주소 추가
	int insertAddress(AddressListDTO addDTO);
	
	// 주소 시퀀스 찾기
	String findAddressSeq();
	
	// 인증 여부 확인
	int idApproval(String id);
}
