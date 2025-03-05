package com.ezotex.comm.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezotex.comm.GridUtil;
import com.ezotex.standard.dto.InfomationDTO;
import com.ezotex.standard.service.impl.StandardServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
// @RequestMapping("/내부 주소/*")
public class MainController {
	
	final StandardServiceImpl service;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@ResponseBody
	@GetMapping("/infomation_list")
	public Map<String, Object> infomation_list() {
		return GridUtil.grid(1, service.getInfomationCount(), service.infomationList());
	}
	
	@ResponseBody
	@GetMapping("/infomationNum")
	public InfomationDTO infomationNum(@RequestParam(name="num") int num) {
		return service.infomationNum(num);
	}
	
	@ResponseBody
	@GetMapping("/infomationUpdate")
	public void infomationUpdate(@RequestParam(name="num") int num, 
			                     @RequestParam(name="title") String title, 
			                     @RequestParam(name="content") String content) {
		service.infomationUpdate(num, title, content);
	}
	
	@ResponseBody
	@GetMapping("/infomationInsert")
	public void infomationInsert(@RequestParam(name="userCode") String userCode, 
			                     @RequestParam(name="title") String title, 
			                     @RequestParam(name="content") String content) {
		service.infomationInsert(userCode, title, content);
	}
	
	@GetMapping("/sample")
	public String sample() {
		return "UISample";
	}
}
