package com.pknu26.miniright.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CBoard {

    private Long cPostId; // 게시판 번호 
    // private Long userId; // 유저 번호
    private String title; // 제목
    private String cContent; // 내용
    private String writer; // 작성자

    private Integer cviewCount; // 조회수

    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일
}