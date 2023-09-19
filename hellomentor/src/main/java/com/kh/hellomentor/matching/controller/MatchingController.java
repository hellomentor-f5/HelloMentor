package com.kh.hellomentor.matching.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kh.hellomentor.matching.model.service.MatchingService;
import com.kh.hellomentor.matching.model.vo.Matching;
import com.kh.hellomentor.matching.model.vo.Mentoring;
import com.kh.hellomentor.member.model.vo.Member;
import com.kh.hellomentor.common.template.Pagination;
import com.kh.hellomentor.common.vo.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes({"loginUser"})
public class MatchingController {


    @Autowired
    private MatchingService  matchingService;

    @GetMapping("/mentoring")
    public String selectList(
            @RequestParam(value="currentPage", defaultValue="1") int currentPage,
            HttpSession session,
            Model model,
            //검색요청이 들어오는경우 paramMap내부에는 keyword, condition
            @RequestParam Map<String, Object> paramMap
    ) {
//
        Member loginUser = (Member) session.getAttribute("loginUser");

        if(loginUser == null) {
            log.info("로그인정보가 없어요유");
        }else {
//
            String memberType = loginUser.getMemberType();
            paramMap.put("memberType", memberType);
            log.info("memberType {}",memberType);


            List<Mentoring> list = matchingService.selectList(currentPage,paramMap);

            //총 게시글 갯수
            int total = matchingService.selectListCount(paramMap);
            int pageLimit = 10;
            int boardLimit = 9;
            PageInfo pi = Pagination.getPageInfo(total, currentPage, pageLimit, boardLimit);

            model.addAttribute("list", list);
            model.addAttribute("param", paramMap);
            model.addAttribute("pi",pi);

            log.info("list {}",list);
            log.info("param {}",paramMap);
            log.info("pi {}",pi);


        }
        return "matching/matching-list";

    }

    //멘토/멘티 등록페이지로 이동
    @GetMapping("/mentoring/insert")
    public String enrollMentoring(
            Model model
    ) {
        return "matching/insertMemberInfo";
    }

    //멘토/멘티 등록
    @PostMapping("/mentoring/insert")
    public String insertMentoring(
            Mentoring mt,
            HttpSession session,
            Model model
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        mt.setUserNo(loginUser.getUserNo()); //사용자 번호 설정도 같이 넣어줘야됨.

        int result = 0;

        try {
            result = matchingService.insertMentoring(mt);
        } catch (Exception e) {
            log.error("error = {}" , e.getMessage());
            //e.printStackTrace();
        }

        if(result > 0 && loginUser.getMemberType().equals("E")) {

            return "redirect:/mentoring";
        }else if(result > 0 && loginUser.getMemberType().equals("O")) {

            return "redirect:/mentoring";
        }else {
            return "common/main";
        }


    }






}





