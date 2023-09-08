package com.kh.hellomentor.board.model.service;

import java.util.List;

<<<<<<< HEAD
=======
import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.matching.model.vo.StudyApplicant;
>>>>>>> 42b6222ba4db5b70f7496f560ed53eacff73f8bf
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.board.model.dao.BoardDao;
import com.kh.hellomentor.board.model.vo.Answer;
import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Free;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Knowledge;
import com.kh.hellomentor.board.model.vo.Reply;
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
    public List<Board> selectNoticeList() {
        return boardDao.selectNoticeList();
    }
    //1-1.공지사항 상세조회
    @Override
    public Board selectNoticeDetail(int postNo){
   	 return boardDao.selectNoticeDetail(postNo);
   }
 
    //2. FAQ 조회
    @Override
    public List<Board> selectFaqList() {
        return boardDao.selectFaqList();
    }

    //3. 1:1문의 작성
    @Override
    public int insertInquiry(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception {
    	
    	board.setPostTitle(Utils.XSSHandling(board.getPostTitle()));
		board.setPostContent(Utils.XSSHandling(board.getPostContent()));
		board.setPostContent(Utils.newLineHandling(board.getPostContent()));
    	
    	int postNo = boardDao.insertInquiry(board);
    
		return postNo;
    }
    @Override
    public int insertInquiry2(Inquiry inquiry) {
    	
    	
    	int result = boardDao.insertInquiry2(inquiry);
    
		return result;
    }
    
    //4. 문의내역 조회
    @Override
    public List<Board> selectInquiryList(int userNo){
    	 return boardDao.selectInquiryList(userNo);
    }
    @Override
    public List<Inquiry> selectInquiryList2(int userNo){
   	 return boardDao.selectInquiryList2(userNo);
   }
    //4-1.문의내역 상세조회
    @Override
    public Board selectInquiryDetail(int postNo){
   	 return boardDao.selectInquiryDetail(postNo);
   }
    @Override
   public Inquiry selectInquiryDetail2(int postNo){
  	 return boardDao.selectInquiryDetail2(postNo);
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
    //5-1. 자유게시판 조회 (화제글 3개)
    @Override
    public List<Board> selectBestFreeList(){
    	 return boardDao.selectBestFreeList();
    }
    @Override
    public List<Free> selectBestFreeList2(){
    	return boardDao.selectBestFreeList2();
   }
    //5-2.공지사항 상세조회
    @Override
    public Board selectFreeDetail(int postNo){
   	 return boardDao.selectFreeDetail(postNo);
   }
    @Override
    public Free selectFreeDetail2(int postNo){
   	 return boardDao.selectFreeDetail2(postNo);
   }
    @Override
    public List<Reply> selectFreeDetailReply(int postNo){
   	 return boardDao.selectFreeDetailReply(postNo);
   }
    //5-3. 자유게시판 글 작성
    @Override
    public int insertFree(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception {
    	
    	board.setPostTitle(Utils.XSSHandling(board.getPostTitle()));
		board.setPostContent(Utils.XSSHandling(board.getPostContent()));
		board.setPostContent(Utils.newLineHandling(board.getPostContent()));
    	
    	int postNo = boardDao.insertInquiry(board);
    
		return postNo;
    }
    @Override
    public int insertFree2(int postNo) {
    	
    	
    	int result = boardDao.insertFree2(postNo);
    
		return result;
    }
 
    
    //6. 지식인 조회 (메인)
    @Override
    public List<Board> selectKnowledgeList(){
    	 return boardDao.selectKnowledgeList();
    }
    @Override
    public List<Knowledge> selectKnowledgeList2(){
    	return boardDao.selectKnowledgeList2();
   }
    @Override
    public List<Answer> selectKnowledgeList3(){
    	return boardDao.selectKnowledgeList3();
   }
    //6-1. 지식인 상세 조회
    public Board selectKnowledgeDetail(int postNo){
      	 return boardDao.selectKnowledgeDetail(postNo);
    };
    public Knowledge selectKnowledgeDetail2(int postNo){
      	 return boardDao.selectKnowledgeDetail2(postNo);
    };
    public List<Board> selectKnowledgeDetailAnswer(int postNo){
      	 return boardDao.selectKnowledgeDetailAnswer(postNo);
    };
    

    
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
    public int insertStudy(Board b) {
        return boardDao.insertStudy(b);
    }

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


}

