package com.pknu26.miniright.service;

import java.util.List;

import com.pknu26.miniright.dto.BBoardComment;
import com.pknu26.miniright.validation.BBoardCommentForm;

public interface BBoardCommentService {

    List<BBoardComment> readCommentList(Long postId);

    void createComment(Long postId, Long userId, BBoardCommentForm form);

    void deleteComment(Long commentId, Long userId);
}