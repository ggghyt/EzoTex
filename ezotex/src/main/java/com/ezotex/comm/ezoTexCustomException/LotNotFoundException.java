package com.ezotex.comm.ezoTexCustomException;

import lombok.Data;

/*
 * 출고 등록시 로트 수량이 없을경우 처리하기 위한 예외 클래스
 * */
@Data
public class LotNotFoundException extends RuntimeException {
	
	private String returnState;
	
    public LotNotFoundException(String returnState) {
        this.setReturnState(returnState);
    }
}
