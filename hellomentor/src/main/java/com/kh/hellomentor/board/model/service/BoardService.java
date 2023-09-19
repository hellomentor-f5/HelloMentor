package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.hellomentor.board.model.vo.*;
import com.kh.hellomentor.matching.model.vo.StudyApplicant;

public interface BoardService {
    List<Board> getPostsByUserNo(int userNo);

    List<Reply> getReplyByUserNo(int userNo);


    //이찬우 구역 시작
    //1. 공지사항 목록 select
    public List<Board> selectNoticeList(int currentPage);

    public int selectNoticeListCount();

    //2. 1:1문의 작성
    public int insertInquiry(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception;

    //3. 문의내역 조회 (메인)
    public List<Board> selectInquiryList();
    public List<Inquiry> selectInquiryList2();
    
    //3-1. 문의내역 조회 (상세)
    public List<Board> selectinquirydetail(int postNo);
    public List<Inquiry> selectinquirydetail2(int postNo);
    
    //4. 자유게시판 조회
    public List<Board> selectFreeList();
    public List<Free> selectFreeList2();
    
    //5. 지식인 조회




    //------------------------------정승훈-----------------------------------------

    List<StudyApplicant> selectPepleList(Map<String, Object> paramMap);

    List<Map<String, Object>> selectRecruitmentCount(Map<String, Object> paramMap);



    Board selectDetailStudy(int postNo);

    int studyDetailApplicant(int postNo);


    int insertBoardAndStudy(Map<String, Object> boardData);


    //-------------------------2023-09-09 정승훈 작업---------------------------
    //스터디 조회
    List<Study> selectStudyList(Study study);

    //스터디참여자수 조회
    int selectStudypeople(int postNo);

    //댓글등록
    int insertReply(Reply r);

    //댓글조회
    List<Reply> selectReplyList(int postNo);

    //-----------------------2023-09-10 정승훈 작업-------------------------------

    //댓글 삭제
    int deleteReply(Reply r);

    //스터디 신청자 등록
    int insertStudyApplicant(StudyApplicant sa);


    int studyDelete(int postNo);


    //페이징처리
    List<Board> selectStudyList(String searchOption, String keyword, int page, int pageSize,Map<String, Object> paramMap);

    long selectStudyListCount(String searchOption, String keyword);

    List<Board> getSideStudyList(int page, int pageSize);

    long selectListCount();


    StudyApplicant duStudy(Map<String, Integer> params);


}
