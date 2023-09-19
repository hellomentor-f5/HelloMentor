package com.kh.hellomentor.member.model.service;

import com.kh.hellomentor.member.model.vo.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.member.model.dao.MemberDao;
import com.kh.hellomentor.member.model.vo.Member;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member loginUser(Member m) {
        return memberDao.loginUser(m);
    }

    @Override
    public int insertMember(Member m) {
        return memberDao.insertMember(m);
    }

    @Override
    public List<Member> getFollowList(int userNo) {
        return memberDao.getFollowList(userNo);
    }

    @Override
    public List<Profile> getProfileList(int userNo) {
        return memberDao.getProfileList(userNo);
    }

    @Override
    public List<Member> getFollowerList(int userNo) {
        return memberDao.getFollwerList(userNo);
    }

    @Override
    public void updateMember(Member updatedMember) {
        memberDao.updateMember(updatedMember);
    }

}

