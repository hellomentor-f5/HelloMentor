package com.kh.hellomentor.member.model.service;

import com.kh.hellomentor.member.model.vo.Member;
import com.kh.hellomentor.member.model.vo.Profile;

import java.util.List;

public interface MemberService {

    Member loginUser(Member m);

    int insertMember(Member m);

    List<Member> getFollowList(int userNo);

    List<Profile> getProfileList(int userNo);

    List<Member> getFollowerList(int userNo);

    void updateMember(Member loginUser);

}