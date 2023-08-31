package com.kh.hellomentor.board.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
    private int postNo;
    private int categoryId;
    private int userNo;
    private int postId;
    private String status;
}
