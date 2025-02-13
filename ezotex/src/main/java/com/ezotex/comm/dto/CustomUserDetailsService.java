package com.ezotex.comm.dto;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ezotex.comm.mappers.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto userDto = userMapper.getUser(username);
		if (userDto == null) {
			throw new UsernameNotFoundException("no id");
		}
		//List<GrantedAuthority> role = new ArrayList<>();
		//userDto.getRoles().forEach(r -> role.add(new SimpleGrantedAuthority(r.getRoleName())));
		//return new User(userDto.getLoginId(), userDto.getPassword(), role);
		
		return new CustomUser(userDto);
	}

}
