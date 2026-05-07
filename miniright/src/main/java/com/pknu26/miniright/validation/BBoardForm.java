package com.pknu26.miniright.validation;

import org.springframework.web.multipart.MultipartFile;

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

    // 기존 이미지 경로
    private String imagePath;

    // 새로 업로드하는 이미지 파일
    private MultipartFile imageFile;
}