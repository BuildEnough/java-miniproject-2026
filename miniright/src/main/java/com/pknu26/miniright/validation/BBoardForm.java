package com.pknu26.miniright.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BBoardForm {

    private Long postId;

    @NotNull(message = "카테고리를 선택하세요.")
    private Long categoryId;

    private Long userId;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String contents;
}