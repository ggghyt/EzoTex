package com.ezotex.comm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
// @RequiredArgsConstructor
// @RequestMapping("/내부 주소/*")
public class MainController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/sample")
	public String sample() {
		return "UISample";
	}
}
