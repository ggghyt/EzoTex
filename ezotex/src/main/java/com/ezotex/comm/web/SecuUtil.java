package com.ezotex.comm.web;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ezotex.comm.dto.CustomUser;
import com.ezotex.comm.dto.UserDto;

public class SecuUtil {
	public static UserDto getUser() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto userDetails = null;
		if (obj != "anonymousUser") {
			userDetails = ((CustomUser)obj).getUserDto();
		} else {
			userDetails = new UserDto();
		}
		return userDetails;
	}
}
