package com.ezotex.standard.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezotex.standard.dto.AddressListDTO;
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
	public String idApproval(String id) {
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

	@Override
	public ResetPasswordDTO findNameEmail(String id) {
		return mapper.findNameEmail(id);
	}

	@Override
	public int passwordUpdate(String id, String password) {
		int emp = mapper.passwordEmpUpdate(id, password);
		int company = mapper.passwordCompanyUpdate(id, password);
		return (emp + company);
	}

	@Override
	public List<Map<String, Object>> productListAll(ProductListInfoDTO productListInfoDTO) {
		return mapper.productListAll(productListInfoDTO);
	}

	@Override
	public List<ProductCategoryDTO> categoryLclas() {
		return mapper.categoryLclas();
	}

	@Override
	public List<ProductCategoryDTO> categorySclas(String lclas) {
		return mapper.categorySclas(lclas);
	}

	@Override
	public int getCount(ProductListInfoDTO productListInfoDTO) {
		return mapper.getCount(productListInfoDTO);
	}

	@Override
	public List<ProductListInfoDTO> unitNameList() {
		return mapper.unitNameList();
	}

	@Override
	public int ProductInfoInsert(ProductListInfoDTO productListInfoDTO) {
		if (productListInfoDTO.getProductCode().length() < 1) {
			return mapper.ProductInfoInsert(productListInfoDTO);
		} else {
			System.out.println(productListInfoDTO.toString());
			return mapper.ProductInfoUpdate(productListInfoDTO);
		}
	}

	@Override
	public List<ProductCategoryDTO> productCategory(String productCode) {
		return mapper.productCategory(productCode);
	}

	@Override
	public int productCategoryInsert(List<ProductCategoryDTO> productCategoryDTO) {
		mapper.productCategoryDelete(productCategoryDTO.get(0).getProductCode());
		System.out.println(productCategoryDTO.toString());
		for (int i = 0 ; i < productCategoryDTO.size() ; i++) {
			mapper.productCategoryInsert(productCategoryDTO.get(i));
		}
		return 0;
	}

	@Override
	public List<CompanyDTO> searchCom(String companyName) {
		return mapper.searchCom(companyName);
	}

	@Override
	public List<ProductOptionDTO> optionSizeList() {
		return mapper.optionSizeList();
	}

	@Override
	public List<ProductOptionDTO> productOption(String productCode) {
		return mapper.productOption(productCode);
	}

	@Override
	public int UpdateOption(List<ProductOptionDTO> productOptionDTO) {
		for (int i = 0 ; i < productOptionDTO.size() ; i++) {
			if (productOptionDTO.get(i).getUnitPrice() < 0) {
				productOptionDTO.get(i).setDiscontinued("YN01");
			} else {
				productOptionDTO.get(i).setDiscontinued("YN02");
			}
			
			if (mapper.countOption(productOptionDTO.get(i)) == 1) {
				mapper.updateOption(productOptionDTO.get(i));
			} else {
				mapper.insertOption(productOptionDTO.get(i));
			}
			System.out.println(productOptionDTO.get(i));
		}
		return 0;
	}

	@Override
	public List<StorageDTO> storageList() {
		return mapper.storageList();
	}

	@Override
	public List<StorageDTO> storageInfoList(String storageCode) {
		return mapper.storageInfoList(storageCode);
	}

	@Override
	public int storageVl(String storageInfoCode) {
		return mapper.storageVl(storageInfoCode);
	}

	@Override
	public int insertStorage(String storageName) {
		return mapper.insertStorage(storageName);
	}

	@Override
	public int updateStorage(StorageDTO storageDTO) {
		return mapper.updateStorage(storageDTO);
	}

	@Override
	public int insertStorageInfo(StorageDTO storageDTO) {
		return mapper.insertStorageInfo(storageDTO);
	}

	@Override
	public int updateStorageInfo(StorageDTO storageDTO) {
		return mapper.updateStorageInfo(storageDTO);
	}

	@Override
	public List<StorageProductDTO> StorageProductList(StorageDTO storageDTO) {
		return mapper.StorageProductList(mapper.findStorageInfoCode(storageDTO));
	}

	@Override
	public List<SafetyStockDTO> SafetyStockList(String productCode) {
		return mapper.SafetyStockList(productCode);
	}

	@Override
	public int updateSafety(SafetyStockDTO safetyStockDTO) {
		return mapper.updateSafety(safetyStockDTO);
	}

	@Override
	public List<Map<String, Object>> infomationList() {
		return mapper.infomationList();
	}

	@Override
	public int getInfomationCount() {
		return mapper.getInfomationCount();
	}

	@Override
	public InfomationDTO infomationNum(int num) {
		return mapper.infomationNum(num);
	}

	@Override
	public int infomationUpdate(int num, String title, String content) {
		return mapper.infomationUpdate(num, title, content);
	}

	@Override
	public int infomationInsert(String userCode, String title, String content) {
		return mapper.infomationInsert(userCode, title, content);
	}

	@Override
	@Transactional
	public int storageInfoColUpdate(int num, String storageCode, int selectCol) {
		mapper.maxColUpdate(num, storageCode);
		mapper.storageInfoColUpdate(num, storageCode, selectCol);
		return 0;
	}

	@Override
	public int storageInfoRowUpdate(int num, String storageCode, int selectRow) {
		mapper.maxRowUpdate(num, storageCode);
		mapper.storageInfoRowUpdate(num, storageCode, selectRow);
		return 0;
	}

	
}
