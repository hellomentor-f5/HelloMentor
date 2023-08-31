package com.kh.hellomentor.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.hellomentor.admin.model.service.adminService;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Report;
import com.kh.hellomentor.board.model.vo.Study;
import com.kh.hellomentor.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class adminController {
   
       @Autowired
      private adminService aService;
   
   	@GetMapping("/admin/selectList")
   	public String selectList( Model model) {
   	 	//public String selectMList( @RequestParam Map<String, Object> paramMap, Model model) {
   		int total = aService.selectListCount();
   		model.addAttribute("aTotal", total);
   		
   		int stotal = aService.selectSListCount();
   		model.addAttribute("sTotal", stotal);
   		
   		int rtotal = aService.selectRListCount();
   		model.addAttribute("rTotal", rtotal);
   		
   		int itotal = aService.selectIListCount();
   		model.addAttribute("iTotal", itotal);
   		
   		return "admin/admin-main";
   	}
   	
	/*
	 * @GetMapping("/admin/selectMList") public String selectSList(Model model) {
	 * 
	 * return "admin/admin-main"; }
	 * 
	 * @GetMapping("/admin/selectMList") public String selectRList(Model model) {
	 * //List<Report> rList = aService.selectRList();
	 * 
	 * return "admin/admin-main"; }
	 * 
	 * @GetMapping("/admin/selectMList") public String selectIList( Model model) {
	 * 
	 * return "admin/admin-main"; }
	 */
}
   
   
