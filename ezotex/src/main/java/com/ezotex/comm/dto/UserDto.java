package com.ezotex.comm.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto /*implements UserDetails*/ {
	private String code;
	private String id;
	private String password;
	private String name;
	private String email;
	private String img;
	
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
