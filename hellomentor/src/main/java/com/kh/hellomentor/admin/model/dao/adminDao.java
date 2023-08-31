package com.kh.hellomentor.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Report;
import com.kh.hellomentor.board.model.vo.Study;
import com.kh.hellomentor.member.model.vo.Member;

@Repository
public class adminDao {

   @Autowired
   private SqlSessionTemplate sqlSession;


public int selectCountList() {
	return sqlSession.selectOne("adminMapper.selectMList");
}

public int selectSCountList() {
	return sqlSession.selectOne("adminMapper.selectSList");
}

public int selectRCountList() {
	return sqlSession.selectOne("adminMapper.selectRList");
}

public int selectICountList() {
	return sqlSession.selectOne("adminMapper.selectIList");
}


   

   
}