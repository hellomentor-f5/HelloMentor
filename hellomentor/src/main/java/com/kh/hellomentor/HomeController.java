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
}



