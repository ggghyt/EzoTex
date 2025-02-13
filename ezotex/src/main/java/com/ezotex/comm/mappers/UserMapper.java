package com.ezotex.comm.mappers;

import java.util.List;

import com.ezotex.comm.dto.RoleDto;
import com.ezotex.comm.dto.UserDto;

public interface UserMapper {
	UserDto getUser(String id);
	List<RoleDto> getRole(Long id);
}
