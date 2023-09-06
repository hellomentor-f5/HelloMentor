package com.kh.hellomentor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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


    @RequestMapping("/free-insert")
    public String free() {
        return "board/free/free-insert";
    }

    @RequestMapping("/free-detail")
    public String freeDetail() {
        return "board/free/free-detail";
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

    @RequestMapping("/knowledge-detail")
    public String knowledgeDetail() {
        return "board/knowledge/knowledge-detail";
    }

    @RequestMapping("/knowledge-question")
    public String knowledgeQuestion() {
        return "board/knowledge/knowledge-question";
    }

    @RequestMapping("/notice-detail")
    public String noticeDetail() {
        return "board/notice/notice-detail";
    }
    
    
    
    

    @RequestMapping("/adminMain")
    public String adminMain() {
        return "admin/admin-main";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
            redirectAttributes.addFlashAttribute("message", "로그아웃 되었습니다.");
        }
        return "redirect:/";
    }
}




