package com.kh.hellomentor.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kh.hellomentor.board.model.vo.*;
import com.kh.hellomentor.matching.model.vo.StudyApplicant;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hellomentor.member.controller.MemberController;

import javax.transaction.Transactional;

@Repository
public class BoardDao {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private SqlSessionTemplate session;

    public List<Board> getPostsByUserNo(int userNo) {
        return session.selectList("boardMapper.getMyPost", userNo);
    }

    public List<Reply> getReplyByUserNo(int userNo) {
        return session.selectList("boardMapper.getMyReply", userNo);
    }


    // 이찬우 구역 시작
    // 1. 공지사항 게시글 조회, 글 갯수 조회 (페이징바) (미완성)
	
    public List<Board> selectNoticeList(int currentPage) {
        int offset = (currentPage - 1) * 5;
        int limit = 5;

        RowBounds rowBounds = new RowBounds(offset, limit);

        return session.selectList("boardMapper.selectNoticeList", rowBounds);
    }

    public int selectNoticeListCount() {
        return session.selectOne("boardMapper.selectNoticeListCount");
    }

    // 2. 1:1문의 등록 (미완성)
    public int insertInquiry(Board board) {
    	int result = 0;
    	result = session.insert("boardMapper.insertInquiry" , board);
		
		if(result > 0) {
			result = board.getPostNo();
			// 게시글 삽입 성공시 selectKey태그를 사용하여 셋팅한 boardNo값을 b에 담아줌.
		}
		
		return result;
    }

    public int insertInquiryAttachment(List<Attachment> list) {
        return session.insert("boardMapper.insertInquiryAttachment", list);
    }
    
    // 3. 문의내역 조회
	
    public List<Board> selectInquiryList() {
        return session.selectList("boardMapper.selectInquiryList");
    }
    public List<Inquiry> selectInquiryList2() {
        return session.selectList("boardMapper.selectInquiryList2");
    }
    
    // 4. 문의내역 상세 조회
    public Board selectinquirydetail(int postNo) {
        return session.selectOne("boardMapper.selectinquirydetail");
    }
    public Inquiry selectinquirydetail2(int postNo) {
        return session.selectOne("boardMapper.selectinquirydetail");
    }
    
    // 5. 자유게시판 조회
	
    public List<Board> selectFreeList() {
        return session.selectList("boardMapper.selectFreeList");
    }
    public List<Free> selectFreeList2() {
        return session.selectList("boardMapper.selectFreeList2");
    }
    //6. 지식인 조회
    //이찬우 구역 끝





    //정승훈 구역





    public List<StudyApplicant> selectPepleList(Map<String, Object> paramMap) {
        return session.selectList("boardMapper.selectPepleList", paramMap);
    }

    public List<Map<String, Object>> selectRecruitmentCount(Map<String, Object> paramMap) {
        return session.selectList("boardMapper.selectRecruitmentCount", paramMap);
    }






    public int insertStudy(Board b) {
        return session.insert("boardMapper.insertStudy",b);
    }

    public Board selectDetailStudy(int postNo) {
        return session.selectOne("boardMapper.selectDetailStudy",postNo);
    }

    public int studyDetailApplicant(int postNo) {
        return session.selectOne("boardMapper.studyDetailApplicant",postNo);
    }



    public int insertBoardAndStudy(Map<String, Object> boardData) {
        int result = 0;
        Board b = (Board) boardData.get("board");
        Study s = (Study) boardData.get("study");
        StudyApplicant sa = (StudyApplicant) boardData.get("studyApplicant");


        // board 테이블에 데이터 삽입
        result = session.insert("boardMapper.insertBoard", b);

        if (result > 0) {
            // board 테이블에 데이터를 삽입한 후 자동 생성된 postNo 값을 가져옴
            int postNo = b.getPostNo();

            // study 테이블에 데이터 삽입
            s.setPostNo(postNo);
            result = session.insert("boardMapper.insertStudy", s);

            if (result > 0) {
                // studyApplicant 테이블에 데이터 삽입
                sa.setPostNo(postNo);
                result = session.insert("boardMapper.insertStudyApplicant1", sa);
            }
        }

        return result;
    }


    //-------------------------2023-09-09 정승훈 작업---------------------------
    public int insertReply(Reply r) {
        return session.insert("boardMapper.insertReply",r);
    }
    public List<Reply> selectReplyList(int postNo) {
        return session.selectList("boardMapper.selectReplyList",postNo);
    }

    //모집인원
    public List<Study> selectStudyList(Study study) {
        return session.selectList("boardMapper.selectStudyList1",study);
    }

    //스터디 작성자 설정한 인원수
    public int selectStudypeople(int postNo) {
        return session.selectOne("boardMapper.selectStudypeople",postNo);
    }


    //댓글삭제
    public int deleteReply(Reply r) {
        return session.delete("boardMapper.deleteReply",r);
    }


    public int insertStudyApplicant(StudyApplicant sa) {
        return session.insert("boardMapper.insertStudyApplicant",sa);
    }


    public int studyDelete(int postNo) {
        return session.delete("boardMapper.studyDelete",postNo);
    }


    public int selectListCount() {
        return session.selectOne("boardMapper.selectSListCount");}

    public int selectStudyListCount(String searchOption, String keyword) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("searchOption", searchOption);
        paramMap.put("keyword", keyword);
        return session.selectOne("boardMapper.selectStudyListCount", paramMap);
    }

    public List<Board> selectStudyList(String searchOption, String keyword, int page, int pageSize, Map<String, Object> paramMap) {
        int start = (page - 1) * pageSize;
        paramMap.put("searchOption", searchOption);
        paramMap.put("keyword", keyword);
        paramMap.put("start", start);
        paramMap.put("pageSize", pageSize);

        return session.selectList("boardMapper.searchStudyList", paramMap);
    }

    public List<Board> getSideStudyList(int page, int pageSize) {
        //페이징 정보를 이용하여 시작 위치를 계산합니다.
        int start = (page - 1) * pageSize;

        // MyBatis 매퍼를 사용하여 데이터베이스에서 페이징된 데이터를 조회합니다.
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("pageSize", pageSize);

        return session.selectList("boardMapper.getSideStudyList", params);
    }


    public StudyApplicant duStudy(Map<String, Integer> params) {
        return session.selectOne("boardMapper.duStudy", params);
    }
}

