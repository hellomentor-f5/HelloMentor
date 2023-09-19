package com.kh.hellomentor.board.controller;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.hellomentor.board.model.vo.*;
import com.kh.hellomentor.matching.model.vo.StudyApplicant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hellomentor.board.model.service.BoardService;
import com.kh.hellomentor.common.Utils;
import com.kh.hellomentor.common.template.Pagination;
import com.kh.hellomentor.common.vo.PageInfo;
import com.kh.hellomentor.member.controller.MemberController;
import com.kh.hellomentor.member.model.vo.Member;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@SessionAttributes({"loginUser"})
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private BoardService boardService;

    @Autowired
    private ServletContext application;


    //    마이페이지 내가 쓴 글
    @RequestMapping("/profile_my_post")
    public String profileMyPost(Model model, HttpSession session) {

        Member loginUser = (Member) session.getAttribute("loginUser");

        int userNo = loginUser.getUserNo();

        List<Board> myPosts = boardService.getPostsByUserNo(userNo);


        // 치환된 값을 사용하도록 수정
        List<Board> postsWithReplacedBoardType = myPosts.stream()
                .map(board -> {
                    String originalBoardType = board.getBoardType();
                    String replacedBoardType = replaceBoardType(originalBoardType);
                    board.setBoardType(replacedBoardType);
                    return board;
                })
                .collect(Collectors.toList());

        Set<String> uniqueBoardTypes = postsWithReplacedBoardType.stream()
                .map(Board::getBoardType)
                .collect(Collectors.toSet());

        model.addAttribute("myPosts", postsWithReplacedBoardType);
        model.addAttribute("boardTypes", uniqueBoardTypes);
        return "mypage/profile_my_post";
    }

    // 마이페이지 내가 쓴 댓글
    @RequestMapping("/profile_my_reply")
    public String profileMyReply(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        int userNo = loginUser.getUserNo();

        List<Reply> myReplies = boardService.getReplyByUserNo(userNo);

        List<Reply> repliesWithReplacedBoardType = myReplies.stream()
                .map(reply -> {
                    String originalBoardType = reply.getBoardType();
                    String replacedBoardType = replaceBoardType(originalBoardType);
                    reply.setBoardType(replacedBoardType);
                    return reply;
                })
                .collect(Collectors.toList());

        Set<String> uniqueBoardTypes = repliesWithReplacedBoardType.stream()
                .map(Reply::getBoardType)
                .collect(Collectors.toSet());

        model.addAttribute("myreply", repliesWithReplacedBoardType);
        model.addAttribute("boardTypes", uniqueBoardTypes);

        return "mypage/profile_my_reply";
    }


    //    게시판 타입 이름으로 바꿔주는 메소드
    private String replaceBoardType(String originalBoardType) {
        switch (originalBoardType) {
            case "A":
                return "문의게시판";
            case "N":
                return "공지사항게시판";
            case "Q":
                return "FAQ게시판";
            case "F":
                return "자유게시판";
            case "K":
                return "지식인게시판";
            case "KA":
                return "지식인답글게시판";
            case "S":
                return "스터디게시판";
            case "R":
                return "신고게시판";
            default:
                return originalBoardType;
        }
    }


    //이찬우 구역 시작
    //1. 공지사항 게시글 조회, 글 갯수 조회 (페이징바)
    @GetMapping("/noticelist")
    public String selectNoticeList(
         @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
         Model model
    ) {

        List<Board> list = boardService.selectNoticeList(currentPage);

        // 총 게시글 갯수
        int total = boardService.selectNoticeListCount();
        int pageLimit = 10;
        int boardLimit = 10;
        PageInfo pi = Pagination.getPageInfo(total, currentPage, pageLimit, boardLimit);
        model.addAttribute("list", list);
        model.addAttribute("pi", pi);

        return "board/notice/notice-board";
    }

    
    
    //2. 문의 내역 insert
    @PostMapping("/insert.iq")
    public String insertBoard(
            Board board,
            @RequestParam(value = "upfile", required = false) List<MultipartFile> upfiles,
			HttpSession session, 
			Model model
    ) {

    	// 이미지, 파일을 저장할 저장경로 얻어오기
		// /resources/images/board/{boardCode}/
		String webPath = "/resources/static/img/attachment";
		String severFolderPath = application.getRealPath(webPath);

		board.setUserNo("2");
		
		// 디렉토리생성 , 해당디렉토리가 존재하지 않는다면 생성
		File dir = new File(severFolderPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		List<Attachment> attachList = new ArrayList();
		
		int level = -1;
		for (MultipartFile upfile : upfiles) {
			// input[name=upFile]로 만들어두면 비어있는 file이 넘어올수 있음.
			level++;
			if (upfile.isEmpty())
				continue;

			// 1. 파일명 재정의 해주는 함수.
			String changeName = Utils.saveFile(upfile, severFolderPath);
			Attachment at = Attachment.
							builder().
							changeName(changeName).
							originName(upfile.getOriginalFilename()).
							build();
			attachList.add(at);
		}
		
		int result = 0;
		
		try {
			result = boardService.insertInquiry(board, attachList, severFolderPath, webPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

        if (result > 0) {
            //session.setAttribute("alertMsg", "게시글 작성에 성공하셨습니다.");
            return "board/inquiry/inquiry-board";
        } else {
           // model.addAttribute("errorMsg", "게시글 작성 실패");
            return "board/inquiry/inquiry-board";
        }
    }
    //3. 문의 내역 조회
    @GetMapping("/inquirylist")
    public String selectInquiryList(
         Model model
    ) {
        List<Board> list = boardService.selectInquiryList();
        model.addAttribute("list", list);
        List<Inquiry> list2 = boardService.selectInquiryList2();
        model.addAttribute("list2", list2);
        
        List<Object[]> combinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            combinedList.add(new Object[] { list.get(i), list2.get(i) });
        }
        model.addAttribute("combinedList", combinedList);

        return "board/inquiry/inquiry-board";
    }
    //3-1. 문의 내역 상세 조회
    @GetMapping("/inquirydetail")
    public String selectInquiryDetail(
			Model model
    ) {
    	 List<Board> list = boardService.selectinquirydetail(2);
         model.addAttribute("list", list);
         List<Inquiry> list2 = boardService.selectinquirydetail2(2);
         model.addAttribute("list2", list2);
         
         List<Object[]> combinedList = new ArrayList<>();
         for (int i = 0; i < list.size(); i++) {
             combinedList.add(new Object[] { list.get(i), list2.get(i) });
         }
         model.addAttribute("combinedList", combinedList);

         return "board/inquiry/inquiry-detail";
         
    }
    //4. 자유게시판 글 조회
    @GetMapping("/freelist")
    public String selectFreeList(
         Model model
    ) {
        List<Board> list = boardService.selectFreeList();
        model.addAttribute("list", list);
        List<Free> list2 = boardService.selectFreeList2();
        model.addAttribute("list2", list2);
        
        List<Object[]> combinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            combinedList.add(new Object[] { list.get(i), list2.get(i) });
        }
        model.addAttribute("combinedList", combinedList);

        return "board/free/free-board";
    }
    //이찬우 구역 끝



    //------------------------------정승훈-----------------------------------------

    //정승훈 스터디화면으로 이동 그리고 조회
    @GetMapping("/study")
    public String selectStudy(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            Model model,
            @RequestParam Map<String, Object> paramMap,
            Board board,
            Study study,
            HttpServletRequest request
    ) {

        //2023-09-08 정승훈 수정

        // Study 데이터 조회
        List<Study> peopleList = boardService.selectStudyList(new Study());

        // Board 데이터 조회
        List<Board> list = boardService.selectStudyList(currentPage, paramMap);

        // Study와 Board 데이터를 연관시켜 Map에 저장
        Map<Integer, Integer> postRecruitmentCountMap1 = new HashMap<>();
        for (Board boardItem : list) {
            for (Study studyItem : peopleList) {
                if (boardItem.getPostNo() == studyItem.getPostNo()) {
                    postRecruitmentCountMap1.put(boardItem.getPostNo(), studyItem.getPeople());
                    break; // 해당하는 Study를 찾았으면 루프 종료
                }
            }
        }


        // 각 게시물의 POST_NO 목록을 가져옵니다.
        List<Integer> postNoList = list.stream()
                .map(Board::getPostNo)
                .collect(Collectors.toList());

        // POST_NO 목록을 paramMap에 추가합니다.
        paramMap.put("POST_NO_LIST", postNoList);

        log.info("paramMap {}" ,paramMap );
        // POST_NO별 RECRUITMENT_COUNT 조회
        List<Map<String, Object>> recruitmentCountList = boardService.selectRecruitmentCount(paramMap);

        log.info("recruitmentCountList {}" ,recruitmentCountList);

        // 각 POST_NO와 RECRUITMENT_COUNT를 매핑하여 Map에 저장
        Map<Integer, Integer> postRecruitmentCountMap = new HashMap<>();
        for (Map<String, Object> entry : recruitmentCountList) {
            Integer postNo = (Integer) entry.get("POST_NO");
            Integer recruitmentCount = ((Long) entry.get("RECRUITMENT_COUNT")).intValue(); // COUNT(*) 결과를 Integer로 변환
            postRecruitmentCountMap.put(postNo, recruitmentCount);
        }


        log.info("postRecruitmentCountMap {}" ,postRecruitmentCountMap );
        // 컨트롤러 모델에 POST_NO별 RECRUITMENT_COUNT를 추가
        model.addAttribute("postRecruitmentCountMap", postRecruitmentCountMap);

        model.addAttribute("postRecruitmentCountMap1", postRecruitmentCountMap1);


        log.info("postRecruitmentCountMap1 {}",postRecruitmentCountMap1);

        log.info("list {}",list);


        // 총 게시글 갯수


        model.addAttribute("param", paramMap); //보드타입
        model.addAttribute("list", list); //study에 대한 정보가 담김
        model.addAttribute("peopleList",peopleList);


        return "/board/study/studyList";
    }




    //스터디등록페이지로 이동
    @GetMapping("/study/insert")
    public String EnrollStudy(
            Model model
    ) {
        return "/board/study/insertStudy";
    }

    //스터디 등록
    @PostMapping("/study/insert")
    public String insertStudy(
            HttpSession session,
            Model model,
            Board board,
            Study study,
            RedirectAttributes redirectAttributes
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        board.setUserNo(loginUser.getUserNo()+""); //작성자의 번호도 넣어주기


        Map<String, Object> boardData = new HashMap<>();
        boardData.put("board", board); // Board 객체를 Map에 추가
        boardData.put("study", study); // People 값을 Map에 추가


        int result = 0;


        result = boardService.insertBoardAndStudy(boardData);



        model.addAttribute("boardData", boardData); //보드타입



        log.info("POST_TITLE{}",board.getPostTitle());
        log.info("POST_CONTENT{}",board.getPostContent());
        log.info("result {}" , result);

        if(result > 0) {
            redirectAttributes.addFlashAttribute("message", "스터디 등록이 완료되었습니다");
            return "redirect:/study";
        }else {
            redirectAttributes.addFlashAttribute("message", "스터디 등록을 실패했습니다.");
            return "common/main";
        }

    }


    //스터디 상세 페이지
    @GetMapping("/study/detail/{postNo}")
    public String detailStudy(
            @PathVariable("postNo") int postNo,
            HttpSession session,
            Model model,
            Board board,
            StudyApplicant studyApplicant,
            HttpServletRequest req,
            HttpServletResponse res
    ) {

        Member loginUser = (Member) session.getAttribute("loginUser");

        //게시글에있는 정보들 조회 (제목,작성자,작성날짜,조회수)
        Board dstudy = boardService.selectDetailStudy(postNo);



        //신청자 인원수 조회
        int studyDetailApplicant = boardService.studyDetailApplicant(postNo);


        //스터디 작성자가 설정한 인원수 가져오기 2023-09-08
        int boardstudypeple = boardService.selectStudypeople(postNo);


//        //댓글리스트 조회
        List<Reply> replyList = boardService.selectReplyList(postNo);

        String url = "";

        log.info("postNo{}",postNo);
        log.info("dstudy {}",dstudy);
        log.info("loginUser {}",loginUser);
        log.info("boardstudypeple {}",boardstudypeple);





        model.addAttribute("replyList",replyList);
        model.addAttribute("loginUser",loginUser);
        model.addAttribute("dstudy",dstudy);
        model.addAttribute("studyDetailApplicant",studyDetailApplicant);
        model.addAttribute("boardstudypeple",boardstudypeple);


        url="board/study/updateStudy";

        //게시글에있는 현재참여인원 조회

        return url;


    }

    //------------------------- 정승훈 작업---------------------------

    @PutMapping("/study/insertReply")
    @ResponseBody
    public int insertReply(Reply r, HttpSession session){

        Member m = (Member) session.getAttribute("loginUser");

        if(m != null){
            r.setUserNo(m.getUserNo()+"");
        }
        int result = boardService.insertReply(r);

        return result;
    }

    @GetMapping("/study/selectReplyList")
    @ResponseBody
    public List<Reply> selectReplyList(int postNo,Model model){
        return boardService.selectReplyList(postNo);
    }







}
