package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.hellomentor.board.model.vo.*;
import com.kh.hellomentor.matching.model.vo.StudyApplicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.board.model.dao.BoardDao;
import com.kh.hellomentor.common.Utils;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Override
    public List<Board> getPostsByUserNo(int userNo) {
        return boardDao.getPostsByUserNo(userNo);
    }

    @Override
    public List<Reply> getReplyByUserNo(int userNo) {
        return boardDao.getReplyByUserNo(userNo);
    }


    //이찬우 구역 시작
    //1. 공지사항 목록 select
    @Override
    public List<Board> selectNoticeList(int currentPage) {
        return boardDao.selectNoticeList(currentPage);
    }

    @Override
    public int selectNoticeListCount() {
        return boardDao.selectNoticeListCount();
    }

    //2. 1:1문의 작성
    @Override
    public int insertInquiry(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception {
    	int postNo = boardDao.insertInquiry(board);
    	
    	int result = 0;
		if(postNo > 0 && !list.isEmpty()) {
			for(Attachment attach    :   list) {
				attach.setPostNo(postNo);
				attach.setFilePath(webPath);
			}
			result = boardDao.insertInquiryAttachment(list);
			
			if(result != list.size()) {// 이미지 삽입 실패시 강제 예외 발생
				throw new Exception("예외발생");
			}
		}
		return result;
    }
    
    //3. 문의내역 조회
    @Override
    public List<Board> selectInquiryList(){
    	 return boardDao.selectInquiryList();
    }
    @Override
    public List<Inquiry> selectInquiryList2(){
   	 return boardDao.selectInquiryList2();
   }
    //4.문의내역 상세조회
    @Override
    public List<Board> selectinquirydetail(int postNo){
   	 return boardDao.selectInquiryList();
   }
    @Override
   public List<Inquiry> selectinquirydetail2(int postNo){
  	 return boardDao.selectInquiryList2();
  }
    
    //5. 자유게시판 조회
    @Override
    public List<Board> selectFreeList(){
    	 return boardDao.selectFreeList();
    }
    @Override
    public List<Free> selectFreeList2(){
    	return boardDao.selectFreeList2();
   }
    //이찬우 구역 끝








    //----------------------------------정승훈----------------------------------
    @Override
    public List<Board> selectStudyList(int currentPage, Map<String, Object> paramMap) {
        return boardDao.selectStudyList(currentPage, paramMap);
    }


    @Override
    public List<StudyApplicant> selectPepleList(Map<String, Object> paramMap) {
        return boardDao.selectPepleList(paramMap);
    }


    //스터디 인원수
    @Override
    public List<Map<String, Object>> selectRecruitmentCount(Map<String, Object> paramMap) {

        return boardDao.selectRecruitmentCount(paramMap);
    }


    //스터디 게시글 등록


    @Override
    public Board selectDetailStudy(int postNo) {
        return boardDao.selectDetailStudy(postNo);
    }

    @Override
    public int studyDetailApplicant(int postNo) {
        return boardDao.studyDetailApplicant(postNo);
    }

    @Override
    public List<Reply> selectReplyList(int postNo) {
        return boardDao.selectReplyList(postNo);
    }

    @Override
    public int insertBoardAndStudy(Map<String, Object> boardData) {
        return boardDao.insertBoardAndStudy(boardData);
    }


    //-------------------------2023-09-09 정승훈 작업---------------------------
    @Override
    public int insertReply(Reply r) {
        return boardDao.insertReply(r);
    }

    @Override
    public List<Study> selectStudyList(Study study) {
        return boardDao.selectStudyList(study);
    }

    @Override
    public int selectStudypeople(int postNo) {
        return boardDao.selectStudypeople(postNo);
    }


}

