package com.yedam.boot.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class MemberVO implements UserDetails{
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberPhone;
	private String memberAddr;
	private String responsibility;
	private String image;
	private String memberRole;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(memberRole));
		return list;
	}
	@Override
	public String getPassword() {
		return memberPw;
	}
	@Override
	public String getUsername() {
		return memberId;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
