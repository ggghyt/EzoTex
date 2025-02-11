package com.ezotex.store.web;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.service.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/store/*")
@RestController
public class InventoryRestControllerTest {

	private final InventoryService service;
	
	@GetMapping("test2")
	public List<InventoryDTO> test() {
		return service.list();
	}

	
}
