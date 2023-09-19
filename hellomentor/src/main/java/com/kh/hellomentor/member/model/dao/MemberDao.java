package com.kh.hellomentor.member.model.dao;

import com.kh.hellomentor.member.model.vo.Profile;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hellomentor.member.model.vo.Member;

import java.util.List;
import java.util.Map;

@Repository
public class MemberDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public Member loginUser(Member m) {
        Member loginUser = sqlSession.selectOne("memberMapper.loginUser", m);
        Profile profile = sqlSession.selectOne("memberMapper.selectProfile", loginUser.getUserNo());
        if (profile != null) {
            loginUser.setProfile(profile.getFilePath() + profile.getChangeName());
        } else {
            loginUser.setProfile("/img/default-profile.jpg");
        }
        return loginUser;
    }

    public int insertMember(Member m) {
        return sqlSession.insert("memberMapper.insertMember", m);
    }

    public List<Member> getFollowList(int userNo) {
        return sqlSession.selectList("memberMapper.getFollowList", userNo);
    }


    public List<Profile> getProfileList(int userNo) {
        return sqlSession.selectList("memberMapper.getProfileList", userNo);
    }

    public List<Member> getFollwerList(int userNo) {
        return sqlSession.selectList("memberMapper.getFollowerList", userNo);
    }

    public void updateMember(Member updatedMember) {
        sqlSession.update("memberMapper.updateProfile", updatedMember);
    }

}