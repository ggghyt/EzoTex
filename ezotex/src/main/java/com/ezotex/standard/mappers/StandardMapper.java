package com.ezotex.standard.mappers;

import com.ezotex.standard.dto.EmpLoginInfoDTO;

public interface StandardMapper {
	// 로그인 검증
	EmpLoginInfoDTO EmpLoginInfo(String emp_id);
}
