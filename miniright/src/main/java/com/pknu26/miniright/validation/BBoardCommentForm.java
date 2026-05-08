package com.pknu26.miniright.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BBoardCommentForm {

    @NotBlank(message = "댓글 내용을 입력하세요.")
    private String contents;
}