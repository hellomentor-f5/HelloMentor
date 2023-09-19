package com.kh.hellomentor.matching.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.kh.hellomentor.matching.model.vo.Mentoring;


@Repository
public class MatchingDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public List<Mentoring> selectList(int currentPage, Map<String, Object> paramMap) {
        int offset = (currentPage -1) * 9;
        int limit  = 9;

        RowBounds rowBounds = new RowBounds(offset,limit);

        return sqlSession.selectList("matchingMapper.selectList",paramMap,rowBounds);
    }

    public int selectListCount(Map<String, Object> paramMap) {
        return sqlSession.selectOne("matchingMapper.selectListCount",paramMap);
    }

    public int selectList(Mentoring mt) {

        return sqlSession.insert("matchingMapper.insertMentoring",mt);

    }



}
