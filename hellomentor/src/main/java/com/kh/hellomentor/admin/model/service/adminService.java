package com.kh.hellomentor.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Report;
import com.kh.hellomentor.board.model.vo.Study;
import com.kh.hellomentor.member.model.vo.Member;

public interface adminService {

	int selectListCount();

	int selectSListCount();

	int selectRListCount();

	int selectIListCount();




	
}

