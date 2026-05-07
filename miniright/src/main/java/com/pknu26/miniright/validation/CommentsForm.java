package com.pknu26.miniright.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentsForm {

    private Long commentId; // 수정 시 필요

    @NotNull(message = "게시글 번호는 필수입니다.")
    private Long postId;

    @NotNull(message = "카테고리 번호는 필수입니다.")
    private Long categoryId;

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Size(max = 1000, message = "댓글은 1000자 이내로 작성해주세요.")
    private String contents;

    private Long userId; // 서비스 계층으로 넘길 때 세션 정보를 담는 용도
}