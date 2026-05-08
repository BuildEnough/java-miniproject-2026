package com.pknu26.miniright.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.miniright.dto.BBoardComment;
import com.pknu26.miniright.mapper.BBoardCommentMapper;
import com.pknu26.miniright.validation.BBoardCommentForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BBoardCommentServiceImpl implements BBoardCommentService {

    private final BBoardCommentMapper bBoardCommentMapper;

    @Override
    public List<BBoardComment> readCommentList(Long postId) {
        return this.bBoardCommentMapper.findByPostId(postId);
    }

    @Override
    public void createComment(Long postId, Long userId, BBoardCommentForm form) {
        BBoardComment comment = new BBoardComment();

        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContents(form.getContents());

        this.bBoardCommentMapper.insertComment(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        this.bBoardCommentMapper.deleteComment(commentId, userId);
    }
}