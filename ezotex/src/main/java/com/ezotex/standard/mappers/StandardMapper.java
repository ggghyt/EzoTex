package com.ezotex.standard.mappers;

import java.util.List;
import java.util.Map;

import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.standard.dto.AddressListDTO;
import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.DivyAddressDTO;
import com.ezotex.standard.dto.EmpDTO;
import com.ezotex.standard.dto.PositionDTO;
import com.ezotex.standard.dto.ProductCategoryDTO;
import com.ezotex.standard.dto.ProductListInfoDTO;
import com.ezotex.standard.dto.ResetPasswordDTO;

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
	
	// company 회원 가입
	int insertCompany(CompanyDTO companyDTO);
	
	// 주소 코드 찾기
	String findDivyAddress(DivyAddressDTO divyAddressDTO);
	
	// 주소 시퀀스 찾기
	String findAddressSeq();
	
	// 인증 여부 확인
	int idApproval(String id);
	
	// 비밀번호 찾기에 사용되는 이름, 이메일 서치
	ResetPasswordDTO findNameEmail(String id);
	
	// 비밀번호 업테이트
	int passwordEmpUpdate(String id, String password);
	int passwordCompanyUpdate(String id, String password);
	
	// 제품 리스트
	List<Map<String, Object>> productListAll(ProductListInfoDTO productListInfoDTO);
	
	// 카테고리 반환
	List<ProductCategoryDTO> categoryLclas();
	List<ProductCategoryDTO> categorySclas(String lclas);
	
	public int getCount(ProductListInfoDTO productListInfoDTO);
}
