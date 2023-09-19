package com.kh.hellomentor.member.controller;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.hellomentor.member.model.vo.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kh.hellomentor.member.model.service.MemberService;
import com.kh.hellomentor.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Slf4j
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService mService;

    @PostMapping("login.me")
    public String loginMember(
            @ModelAttribute Member m,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Member loginUser = mService.loginUser(m);
        String url = "";
        if (loginUser != null) {
            if (loginUser.getUserId().equals("admin")) {
                session.setAttribute("loginUser", loginUser);
                redirectAttributes.addFlashAttribute("message", "관리자로 로그인했습니다.");
                url = "redirect:/adminMain";
            } else {
                session.setAttribute("loginUser", loginUser);
                redirectAttributes.addFlashAttribute("message", loginUser.getUserName() + "님 반갑습니다");
                url = "redirect:/main";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "아이디 또는 비밀번호를 확인해주세요.");
            url = "redirect:/";
        }
        return url;
    }

    @PostMapping("/sign.up")
    public String insertMember(@Validated Member m, HttpSession session, Model model, BindingResult bindingResult) {
        int result = mService.insertMember(m);
        String url = "";

        System.out.println(m);
        if (result > 0) {
            //성공시
            model.addAttribute("message", "회원가입을 축하드립니다. 로그인 해주세요");
            url = "login/login";
        } else {
            //실패 - 에러페이지로
            model.addAttribute("message", "회원가입 실패");
            url = "login/login";
        }
        return url;
    }

    @RequestMapping("/home_follow")
    public String homeFollow() {
        return "mypage/home_follow";
    }

    @RequestMapping("/home_following_list")
    public String getFollowList(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        int userNo = loginUser.getUserNo();
        List<Member> followingList = mService.getFollowList(userNo);
        List<Profile> profileList = mService.getProfileList(userNo);

        List<Map<String, Object>> combinedList = new ArrayList<>();
        for (Member member : followingList) {
            Map<String, Object> combinedInfo = new HashMap<>();
            combinedInfo.put("member", member);

            Profile profile = null;
            for (Profile p : profileList) {
                if (p.getUserNo() == member.getUserNo()) {
                    profile = p;
                    break;
                }
            }

            if (profile != null) {
                combinedInfo.put("profile", profile);
            } else {
                Profile defaultProfile = new Profile();
                defaultProfile.setFilePath("/img/");
                defaultProfile.setChangeName("default-profile.jpg");
                combinedInfo.put("profile", defaultProfile);
            }

            combinedList.add(combinedInfo);
        }

        model.addAttribute("combinedList", combinedList);
        logger.info("Combined List: {}", combinedList);
        return "mypage/home_following_list";
    }


    @RequestMapping("/home_follower_list")
    public String getFollowerList(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int userNo = loginUser.getUserNo();
        List<Member> followerList = mService.getFollowerList(userNo);
        List<Profile> profileList = mService.getProfileList(userNo);

        List<Map<String, Object>> combinedList = new ArrayList<>();
        for (Member member : followerList) {
            Map<String, Object> combinedInfo = new HashMap<>();
            combinedInfo.put("member", member);

            Profile profile = null;
            for (Profile p : profileList) {
                if (p.getUserNo() == member.getUserNo()) {
                    profile = p;
                    break;
                }
            }

            if (profile != null) {
                combinedInfo.put("profile", profile);
            } else {
                Profile defaultProfile = new Profile();
                defaultProfile.setFilePath("/img/");
                defaultProfile.setChangeName("default-profile.jpg");
                combinedInfo.put("profile", defaultProfile);
            }

            combinedList.add(combinedInfo);
        }

        model.addAttribute("combinedList", combinedList);
        logger.info("Combined List: {}", combinedList);
        return "mypage/home_follower_list";
    }


    @RequestMapping("/profile_edit_info")
    public String profileEdit(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if ("E".equals(loginUser.getMemberType())) {
            loginUser.setMemberType("멘티");
        } else if ("O".equals(loginUser.getMemberType())) {
            loginUser.setMemberType("멘토");
        }
        model.addAttribute("loginUser", loginUser);
        return "mypage/profile_edit_info";
    }


    @PostMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestBody Map<String, String> requestBody, HttpSession session) {
        String originPwd = requestBody.get("originPwd");
        String newPwd = requestBody.get("newPwd");
        String intro = requestBody.get("intro");

        Member loginUser = (Member) session.getAttribute("loginUser");

        if (!originPwd.equals(loginUser.getUserPwd())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재 비밀번호가 일치하지 않습니다.");
        }

        loginUser.setUserPwd(newPwd);
        loginUser.setIntroduction(intro);
        mService.updateMember(loginUser);

        return ResponseEntity.ok("프로필이 업데이트되었습니다.");
    }


}












   
