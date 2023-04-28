package com.yedam.boot.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yedam.boot.member.MemberVO;
import com.yedam.boot.member.mapper.MemberMapper;
import com.yedam.boot.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService, UserDetailsService {
	
	@Autowired
	MemberMapper mapper;

	@Override
	public MemberVO getMember(String id) {
		return mapper.getMember(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO memberVO = mapper.getMember(username);
		if(memberVO == null) {
			throw new UsernameNotFoundException("no userid");
		}
		return memberVO;
	}
	
}
