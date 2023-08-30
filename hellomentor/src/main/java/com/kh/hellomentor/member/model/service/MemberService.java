package com.kh.hellomentor.member.model.service;

import com.kh.hellomentor.member.model.vo.Member;

public interface MemberService {

   Member loginUser(Member m);

      int insertMember(Member m);
}