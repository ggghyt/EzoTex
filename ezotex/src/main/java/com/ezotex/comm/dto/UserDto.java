package com.ezotex.comm.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto /*implements UserDetails*/ {
	private String id;
	private String password;
	private String name;
	
	private List<RoleDto> roles;
	/*
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> role = new ArrayList<>();
		roles.forEach(r -> role.add(new SimpleGrantedAuthority(r.getRoleName())));
		return role;
	}

	@Override
	public String getUsername() {
		return loginId;
	}
	*/
}
