package com.ezotex.comm.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ezotex.comm.dto.CustomUser;
import com.ezotex.comm.dto.UserDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		UserDto userDto = ((CustomUser)auth.getPrincipal()).getUserDto();
		
		request.getSession().setAttribute("code", (String)userDto.getCode());
		request.getSession().setAttribute("id", (String)userDto.getId());
		request.getSession().setAttribute("name", (String)userDto.getName());
		request.getSession().setAttribute("email", (String)userDto.getEmail());
		request.getSession().setAttribute("img", (String)userDto.getImg());
		
		List<String> roleNames = new ArrayList<>();
		
		auth.getAuthorities().forEach(authrority -> {
			roleNames.add(authrority.getAuthority());
		});
		
		if (roleNames.contains("ROLE_EMP")) {
			response.sendRedirect("/");
			return;
		} else if (roleNames.contains("ROLE_SUPPLY")) {
			response.sendRedirect("/");
			return;
		}
		response.sendRedirect("/login/main");
	}

}
