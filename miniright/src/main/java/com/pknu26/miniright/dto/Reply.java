package com.pknu26.miniright.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

    private Long postId; // 게시글 ID

    private Long replyId; // 댓글 ID

    private String contents; // 댓글 내용

    private String writer; // 댓글 작성자

    private Long userId; // 유저 아이디

    private LocalDateTime createdAt; // 댓글 작성 시간

}
