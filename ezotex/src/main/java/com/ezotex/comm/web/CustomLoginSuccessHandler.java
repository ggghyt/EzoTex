package com.ezotex.comm.web;

import java.io.IOException;
import java.io.PrintWriter;
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
		
		request.getSession().setAttribute("code", userDto.getCode());
		request.getSession().setAttribute("id", (String)userDto.getId());
		request.getSession().setAttribute("name", userDto.getName());
		request.getSession().setAttribute("email", userDto.getEmail());
		request.getSession().setAttribute("img", userDto.getImg());
		
		List<String> roleNames = new ArrayList<>();
		
		auth.getAuthorities().forEach(authrority -> {
			roleNames.add(authrority.getAuthority());
		});
		
        //ROLE_DRIVER인 경우(배송기사인 경우) JSON 응답 반환
        if (roleNames.contains("ROLE_DRIVER")) {
            //response.setContentType("application/json;charset=UTF-8");	//제이슨 형식으로 클라이언트로 데이터를 보내야됨. 콘텐트 타입 설정.
            //response.setStatus(HttpServletResponse.SC_OK);
            
            //PrintWriter out = response.getWriter();
            //out.write("{\"message\":\"로그인 성공\",\"role\":\"" + userDto.getRoles().get(0).getRole() + "\",\"id\":\"" + (String)userDto.getId() + "\",\"name\":\"" + userDto.getName() + "\"}");
            //out.flush();
        	
        	response.sendRedirect("/delivery/driveList");
            return; //리턴하여 아래코드는 실행되지 않게함.
        }
        
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
