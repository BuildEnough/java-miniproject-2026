package com.pknu26.miniright.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CBoardForm {

    private Long cPostId;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    
    @NotBlank(message = "내용은 필수입니다.")
    private String cContent;

    @NotBlank(message = "작성자를 입력하세요.")
    private String writer;

}
