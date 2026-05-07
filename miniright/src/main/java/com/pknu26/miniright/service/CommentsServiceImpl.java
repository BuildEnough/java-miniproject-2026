package com.pknu26.miniright.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.pknu26.miniright.dto.Comments;
import com.pknu26.miniright.mapper.CommentsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsMapper commentsMapper;

    // 댓글 목록 조회
    @Override
    public List<Comments> getCommentsList(Long postId) {
        return this.commentsMapper.selectCommentsList(postId);
    }

    // 댓글 등록
    @Override
    public void registerComment(Comments comments) {
        this.commentsMapper.insertComment(comments);
    }

    // 댓글 수정
    @Override
    public void modifyComment(Comments comments) {
        this.commentsMapper.updateComment(comments);
    }

    // 댓글 삭제
    @Override
    public void removeComment(Long commentId) {
        this.commentsMapper.deleteComment(commentId);
    }
}