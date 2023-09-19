package com.kh.hellomentor.matching.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;


import com.kh.hellomentor.matching.model.vo.Mentoring;

public interface MatchingService {

    List<Mentoring> selectList(int currentPage, Map<String, Object> paramMap);

    public int insertMentoring(Mentoring mt);

    int selectListCount(Map<String, Object> paramMap);
}
