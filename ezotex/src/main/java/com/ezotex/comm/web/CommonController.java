package com.ezotex.comm.web;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezotex.comm.dto.UserInfoDto;
import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.PositionDTO;
import com.ezotex.standard.service.impl.StandardServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login/*")
public class CommonController {

	final StandardServiceImpl service;
	
    // 로그인 메인 페이지
	@GetMapping("/main")
	public String login() {
		return "/login/login";
	}

    // 회원가입 메인 페이지
	@GetMapping("/register_main")
	public String register_main() {
		return "/login/register_main";
	}

    // 공급업체 회원가입 페이지
	@GetMapping("/register_supply")
	public String register_supply() {
		return "/login/register_supply";
	}

	// 공급업체 회원가입 페이지
	@GetMapping("/register_emp")
	public String register_emp() {
		return "/login/register_emp";
	}
	
	// 로그인 중복 확인 메세지
	@ResponseBody
	@GetMapping("/searchId")
	public int searchId(@RequestParam(name="id") String id) {
		return service.searchId(id);
	}
	
	// 로그인 세션 정보 넘겨주기
	@ResponseBody
	@GetMapping("/userInfo")
	public UserInfoDto userInfo(HttpSession session) {
		UserInfoDto userInfo = new UserInfoDto();
		userInfo.setCode((String)session.getAttribute("code"));
		userInfo.setId((String)session.getAttribute("id"));
		userInfo.setName((String)session.getAttribute("name"));
		userInfo.setEmail((String)session.getAttribute("email"));
		userInfo.setImg((String)session.getAttribute("img"));
		
		return userInfo;
	}
	
	// dept 리스트
	@ResponseBody
	@GetMapping("/deptList")
	public List<DeptDTO> deptList() {
		return service.deptList();
	}
	
	// position 리스트
	@ResponseBody
	@GetMapping("/positionList")
	public List<PositionDTO> positionList() {
		return service.positionList();
	}
}
