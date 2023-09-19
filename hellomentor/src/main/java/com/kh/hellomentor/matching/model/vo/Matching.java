package com.kh.hellomentor.matching.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matching {
    private int matchNo;
    private int matchingRegisNo;
    private int mentorRegisNo;
    private int menteeRegisNo;
    private String status;
    private Date createDate;
}
