package com.kh.hellomentor.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hellomentor.member.model.vo.Member;

@Repository
public class MemberDao {

   @Autowired
   private SqlSessionTemplate sqlSession;
   
   public Member loginUser(Member m) {
      return sqlSession.selectOne("memberMapper.loginUser", m);
   }

public int insertMember(Member m) {
	return sqlSession.insert("memberMapper.insertMember", m);
}
   
}