package com.kh.hellomentor.matching.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.matching.model.dao.MatchingDao;
import com.kh.hellomentor.matching.model.vo.Matching;
import com.kh.hellomentor.matching.model.vo.Mentoring;

@Service
public class MatchingServiceImpl implements MatchingService{

    @Autowired
    private MatchingDao matchingDao;



    @Override
    public List<Mentoring> selectList(int currentPage,Map<String, Object> paramMap) {
        return matchingDao.selectList(currentPage,paramMap);
    }

    @Override
    public int selectListCount(Map<String, Object> paramMap) {
        return matchingDao.selectListCount(paramMap);
    }


    @Override
    public int insertMentoring(Mentoring mt) {
        return matchingDao.selectList(mt);
    }




}
