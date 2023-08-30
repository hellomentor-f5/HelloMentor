package com.kh.hellomentor.board.model.service;

import com.kh.hellomentor.board.model.vo.Board;

import java.util.List;

public interface BoardService {
    List<Board> getPostsByUserNo(int userNo);
}
