package com.pknu26.miniright.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BBoardForm {

    private Long postId;
    private Long categoryId;
    private Long userId;
    
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String contents;
}
