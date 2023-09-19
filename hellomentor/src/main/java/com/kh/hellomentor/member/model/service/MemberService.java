package com.kh.hellomentor.member.model.service;

import com.kh.hellomentor.member.model.vo.Member;
import com.kh.hellomentor.member.model.vo.Profile;

import java.util.List;
import java.util.Map;

public interface MemberService {



    Member loginUser(Member m);

    int insertMember(Member m);

    List<Member> getFollowList(int userNo);

    List<Profile> getProfileList(int userNo);

    List<Member> getFollowerList(int userNo);

    void updateMember(Member loginUser);

    //정승훈 토큰충전
    int insertUpdateToken(Map<String, Object> tokenData);

    int exchangeToken(Member m);

    int getUpdateToken(int userNo);

    int paymentResult(int userNo);
}