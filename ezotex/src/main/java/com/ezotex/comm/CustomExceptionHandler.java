package com.ezotex.comm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ezotex.comm.ezoTexCustomException.LotNotFoundException;



/*
 * RestController요청에 대해서만 처리합니다.
 * 일반 controller에 사용자 정의 예외를 처리하려면 
 * @ControllerAdvice를 추가한 핸들러 클래스에서 처리하면 됩니다.
 * */
@RestControllerAdvice
public class CustomExceptionHandler {
	
	//로트가 부족하거나, 없을경우 예외처리
	@ExceptionHandler(LotNotFoundException.class)
	public Map<String, String> lotNotFound(LotNotFoundException notLot) {
		//빈 해쉬맵 생성
		Map<String, String> errorResponse = new HashMap<>();
		
		errorResponse.put("state", notLot.getReturnState());
		
		//화면으로 가져가는 데이터
		return errorResponse;
	}
}
