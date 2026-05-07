package com.pknu26.miniright.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReplyForm {

    private Long replyId; // 답글 번호

    private Long postId; // 게시글 번호

    @NotBlank(message = "댓글 내용은 필수입니다.") 
    private String contents; // 답글 내용
    
    @NotBlank(message = "작성자는 필수입니다.")
    private String writer; // 답글 작성자
    
    private Long userId; // 유저 ID
}
