package com.pknu26.miniright.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BBoard {

    private Long bPostId; // 게시판 제목
    private Long categoryId;  // 카테고리 번호
    private Long userId;  // 유저 번호
    private String title;  // 제목
    private String bContent;  // 내용
    
    private Integer bviewCount; // 조회수
    
    private LocalDateTime createdAt;  // 생성일
    private LocalDateTime updatedAt;  // 수정일
}
