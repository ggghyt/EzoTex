package com.ezotex.comm.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class RestUserController {
	@GetMapping("/session-info")
    public ResponseEntity<?> getSessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션 없음");
        }

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("id", session.getAttribute("id"));
        sessionData.put("name", session.getAttribute("name"));
        sessionData.put("email", session.getAttribute("email"));
        sessionData.put("img", session.getAttribute("img"));

        return ResponseEntity.ok(sessionData);
    }
	
	@GetMapping("/csrf")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
	    return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
	}
	
}
