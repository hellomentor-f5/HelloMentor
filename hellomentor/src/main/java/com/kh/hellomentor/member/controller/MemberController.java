package com.kh.hellomentor.member.controller;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.hellomentor.member.model.vo.Payment;
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


    //정승훈 회원 토큰 충전
    @PostMapping("/insert/token")
    public String insertToken(
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes,
            Payment payment,
            Member m,
            @RequestParam(name = "token", defaultValue = "") String token
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int newToken = Integer.parseInt(token); //선택된 토큰
        int currentToken = loginUser.getToken(); //기존에 있던 토큰
        int updatedToken = currentToken + newToken; //선택된 토큰과 + 기존에 있던 토큰

        m.setToken(updatedToken); // 업데이트된 토큰 값을 Member 객체에 설정
        m.setUserNo(loginUser.getUserNo());
        payment.setUserNo(loginUser.getUserNo());


        // token 값에 따라 price 설정
        int price;
        switch (token) {
            case "10":
                price = 1000;
                break;
            case "50":
                price = 5000;
                break;
            case "100":
                price = 10000;
                break;
            case "200":
                price = 20000;
                break;
            case "500":
                price = 50000;
                break;
            default:
                price = 0;
                break;
        }
        payment.setPrice(price); // payment 객체에 가격 설정

        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("payment", payment);
        tokenData.put("m", m);


        int result = mService.insertUpdateToken(tokenData);
        log.info("tokenData {}", tokenData);
        log.info("result {}", result);




        int updateToken = mService.getUpdateToken(loginUser.getUserNo());
        //변경된 토큰의 값을 다시 새로 세션에 담아줘야됨.
        loginUser.setToken(updateToken);
        session.setAttribute("loginUser", loginUser);



        if (result >= 0) {
            redirectAttributes.addFlashAttribute("message", "토큰충전 완료되었습니다");
            return "redirect:/main";
        } else {
            redirectAttributes.addFlashAttribute("message", "토큰충전을 실패했습니다.");
            return "common/main";
        }
    }

    @PostMapping("/exchange/token")
    public String exchageToken(
            HttpSession session,
            Member m,
            Model model,
            Payment payment,
            RedirectAttributes redirectAttributes) {

        Member loginUser = (Member) session.getAttribute("loginUser");

        m.setUserNo(loginUser.getUserNo());
        payment.setUserNo(loginUser.getUserNo());
        log.info("loginUser {}", loginUser.getToken());

        if (loginUser.getToken() == 0) {
            model.addAttribute("message", "토큰이 비어있습니다. 충전페이지로 이동합니다.");
            return "token/tokenInsert";
        } else {
            log.info("loginUser {}", loginUser.getToken());

            int result = mService.exchangeToken(m);

            // 변경된 토큰의 값을 가져오기
            int updateToken = mService.getUpdateToken(loginUser.getUserNo());
            int paymentresult = mService.paymentResult(loginUser.getUserNo());

            //변경된 토큰의 값을 다시 새로 세션에 담아줘야됨.
            loginUser.setToken(updateToken);
            session.setAttribute("loginUser", loginUser);

            log.info("result {}", result);
            log.info("loginUser {}", loginUser.getToken());

            model.addAttribute("token", loginUser.getToken());

            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "토큰환전이 완료되었습니다.");
                return "redirect:/main";
            } else {
                model.addAttribute("message", "오류발생");
                return "main";
            }
        }
    }
}












   
