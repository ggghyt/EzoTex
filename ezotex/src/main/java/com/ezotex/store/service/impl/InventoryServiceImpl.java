package com.ezotex.store.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.mappers.InventoryMapper;
import com.ezotex.store.service.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryMapper mapper;
	
	@Override
	public List<InventoryDTO> list() {
		return mapper.list();
	}
	
}
