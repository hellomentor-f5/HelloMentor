package com.kh.hellomentor.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.hellomentor.member.model.service.MemberService;
import com.kh.hellomentor.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController {
   
       @Autowired
      private MemberService mService;
   
      @PostMapping("login.me")
      public String loginMember(
                        @ModelAttribute Member m , 
                        HttpSession session , 
                        Model model
            ) {
         Member loginUser = mService.loginUser(m);
         String url = "";
         if(loginUser != null) {
        	 if(loginUser.getUserId().equals("admin")){
        		 session.setAttribute("loginUser", loginUser);
        		 model.addAttribute("message","관리자로 로그인했습니다.");
                 url = "admin/admin-main";
        	 }else {
        		 session.setAttribute("loginUser", loginUser);
        		 model.addAttribute("message", loginUser.getUserName()+"님 반갑습니다");
                 url = "common/main";
        	 }
        	 }else {
        		 model.addAttribute("message","아이디 또는 비밀번호를 확인해주세요.");
        		 url = "login/login";
        	 }
         return url;
      }
      @PostMapping("/sign.up")
  	public String insertMember(@Validated Member m , HttpSession session, Model model ,BindingResult bindingResult) {
  		int result = mService.insertMember(m);
  		String url = "";
  		
  		System.out.println(m);
  		if(result > 0) {
  			//성공시
  			model.addAttribute("message","회원가입을 축하드립니다. 로그인 해주세요");
   		 url = "login/login";
  		}else {
  			//실패 - 에러페이지로
  			model.addAttribute("message","회원가입 실패");
  	   		 url = "login/login";
  		}
  		return url;
  	}
  	
}
   
   
