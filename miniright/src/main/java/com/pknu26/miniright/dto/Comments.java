package com.pknu26.miniright.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    private Long commentId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long postId;
    private Long categoryId;
    private Long userId;
    private String loginId;
}