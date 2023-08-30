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
    
   
    
}




