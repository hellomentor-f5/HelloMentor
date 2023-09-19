package com.kh.hellomentor.matching.model.service;

import java.util.List;
import java.util.Map;

import com.kh.hellomentor.member.model.vo.Follow;
import com.kh.hellomentor.member.model.vo.Member;
import com.kh.hellomentor.member.model.vo.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.matching.model.dao.MatchingDao;
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
    public int insertMentoring(Mentoring mt) {
        return matchingDao.selectList(mt);
    }



    @Override
    public int selectMentorListCount(String searchOption, String keyword, int memberTypeId) {
        return matchingDao.selectMentorListCount(searchOption, keyword,memberTypeId);
    }

    @Override
    public List<Mentoring> selectMentorList(String searchOption, String keyword, int page, int pageSize, Map<String, Object> paramMap) {
        return matchingDao.selectMentorList(searchOption,keyword,page,pageSize,paramMap);
    }

    @Override
    public int selectListCount(int memberTypeId) {
        return matchingDao.selectListCount(memberTypeId);
    }

    @Override
    public List<Mentoring> getSideMentorList(int page, int pageSize, String memberType) {
        return matchingDao.getSideMentorList(page,pageSize,memberType);
    }

    @Override
    public Member getMemberByPostNo(int postNo) {
        return matchingDao.getMemberByPostNo(postNo);
    }

    @Override
    public Profile getProfileByPostNo(int postNo) {
        return matchingDao.getProfileByPostNo(postNo);
    }

    @Override
    public Mentoring getMentoringByPostNo(int postNo) {
        return matchingDao.getMentoringByPostNo(postNo);
    }

    @Override
    public int insertPaymentMatching(Map<String, Object> paymentData) {
        return matchingDao.insertPaymentMatching(paymentData);
    }

    @Override
    public int getUpdateToken(int userNo) {
        return matchingDao.getUpdateToken(userNo);
    }

    @Override
    public int suggestMatching(Map<String, Object> suggestData) {
        return matchingDao.suggestMatching(suggestData);
    }

    @Override
    public int follow(Follow follow) {
        return matchingDao.follow(follow);
    }

    @Override
    public int unfollow(Follow follow) {
        return matchingDao.unfollow(follow);
    }


}
