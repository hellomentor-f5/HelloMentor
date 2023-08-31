package com.kh.hellomentor.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.hellomentor.admin.model.dao.adminDao;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Report;
import com.kh.hellomentor.board.model.vo.Study;
import com.kh.hellomentor.member.model.vo.Member;


@Service
public class adminServiceImpl implements adminService{

	@Autowired
	   private adminDao adminDao;
	
	
	@Override
	public int selectListCount() {
		return adminDao.selectCountList();
	}
	@Override
	public int selectSListCount() {
		return adminDao.selectSCountList();
	}
	@Override
	public int selectRListCount() {
		return adminDao.selectRCountList();
	}
	@Override
	public int selectIListCount() {
		 return adminDao.selectICountList();
	}
	
	
	
	
	
	
	
	
}