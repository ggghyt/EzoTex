package com.ezotex.standard.mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ezotex.standard.dto.AddressListDTO;
import com.ezotex.standard.dto.CalenderDTO;
import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.DivyAddressDTO;
import com.ezotex.standard.dto.EmpDTO;
import com.ezotex.standard.dto.InfomationDTO;
import com.ezotex.standard.dto.PositionDTO;
import com.ezotex.standard.dto.ProductCategoryDTO;
import com.ezotex.standard.dto.ProductListInfoDTO;
import com.ezotex.standard.dto.ProductOptionDTO;
import com.ezotex.standard.dto.ResetPasswordDTO;
import com.ezotex.standard.dto.SafetyStockDTO;
import com.ezotex.standard.dto.StorageDTO;
import com.ezotex.standard.dto.StorageProductDTO;

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
	String idApproval(String id);
	
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
	
	int getCount(ProductListInfoDTO productListInfoDTO);
	
	// 제품 단위 리스트
	List<ProductListInfoDTO> unitNameList();
	
	// 제품 생성
	int ProductInfoInsert(ProductListInfoDTO productListInfoDTO);
	
	int ProductInfoUpdate(ProductListInfoDTO productListInfoDTO);
	
	// 카테고리 리스트
	List<ProductCategoryDTO> productCategory(String productCode);
	
	// 카테고리 삭제, 추가
	int productCategoryDelete(String productCode);
	
	int productCategoryInsert(ProductCategoryDTO productCategoryDTO);
	
	// 기업 리스트
	List<CompanyDTO> searchCom(String companyName);
	
	// 옵션 리스트
	List<ProductOptionDTO> optionSizeList();
	
	// 단일 제품 옵션 리스트
	List<ProductOptionDTO> productOption(String productCode);
	
	// 같은 옵션 존재하는 카운트
	int countOption(ProductOptionDTO productOptionDTO);
	
	// 옵션 update
	int updateOption(ProductOptionDTO productOptionDTO);
	
	// 옵션 insert
	int insertOption(ProductOptionDTO productOptionDTO);
	
	// 창고 리스트
	List<StorageDTO> storageList();
	
	List<StorageDTO> storageInfoList(String storageCode);
	
	// 개별 창고 적재된 부피
	int storageVl(String storageInfoCode);
	
	// 창고 목록 관리
	int insertStorage(String storageName);
	
	int updateStorage(StorageDTO storageDTO);
	
	// 창고 항목 관리
	int insertStorageInfo(StorageDTO storageDTO);
	
	int updateStorageInfo(StorageDTO storageDTO);
	
	// 창고 항목 상세 리스트
	List<StorageProductDTO> StorageProductList(int storageInfoCode);
	
	int findStorageInfoCode(StorageDTO storageDTO);
	
	List<SafetyStockDTO> SafetyStockList(String productCode);
	
	int updateSafety(SafetyStockDTO safetyStockDTO);
	
	List<Map<String, Object>> infomationList();
	
	int getInfomationCount();
	
	InfomationDTO infomationNum(int num);
	
	int infomationUpdate(int num, String title, String content);
	
	int infomationInsert(String userCode, String title, String content);
	
	int maxColUpdate(int num, String storageCode);
	
	int storageInfoColUpdate(int num, String storageCode, int selectCol);
	
	int maxRowUpdate(int num, String storageCode);
	
	int storageInfoRowUpdate(int num, String storageCode, int selectRow);
	
	List<CalenderDTO> calendarInfo();
	
	List<CalenderDTO> calendarCount();
	
	List<ProductListInfoDTO> mainProductImg();
	
	Map<String, BigDecimal> mainTodayCount();
}
