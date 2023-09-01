package com.kh.hellomentor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "login/login";
    }

    @RequestMapping("/mypage")
    public String myPage() {
        return "mypage/mypage";
    }


    @RequestMapping("/main")
    public String main() {
        return "common/main";
    }
    
    @RequestMapping("/choose-sign-up")
    public String chooseSignUp() {
        return "login/choose-sign-up";
    }
    @RequestMapping("/sign-up")
    public String signUp() {
        return "login/sign-up";
    }
    
    
    
    
    
    //이찬우 페이지 시작
    @RequestMapping("/faq-board")
    public String faqBoard() {
        return "board/faq/faq-board";
    }
    @RequestMapping("/free")
    public String free() {
        return "board/free/free";
    }
    @RequestMapping("/free-board")
    public String freeBoard() {
        return "board/free/free-board";
    }
    @RequestMapping("/free-detail")
    public String freeDetail() {
        return "board/free/free-detail";
    }
    @RequestMapping("/inquiry-board")
    public String inquiryBoard() {
        return "board/inquiry/inquiry-board";
    }
    @RequestMapping("/inquiry-detail")
    public String inquiryDetail() {
        return "board/inquiry/inquiry-detail";
    }
    @RequestMapping("/inquiry-insert")
    public String inquiryInsert() {
        return "board/inquiry/inquiry-insert";
    }
    @RequestMapping("/knowledge-answer")
    public String knowledgeAnswer() {
        return "board/knowledge/knowledge-answer";
    }
    @RequestMapping("/knowledge-board")
    public String knowledgeBoard() {
        return "board/knowledge/knowledge-board";
    }
    @RequestMapping("/knowledge-detail")
    public String knowledgeDetail() {
        return "board/knowledge/knowledge-detail";
    }
    @RequestMapping("/knowledge-question")
    public String knowledgeQuestion() {
        return "board/knowledge/knowledge-question";
    }
    @RequestMapping("/notice-board")
    public String noticeBoard() {
        return "board/notice/notice-board";
    }
    @RequestMapping("/notice-detail")
    public String noticeDetail() {
        return "board/notice/notice-detail";
    }
}




