package com.pknu26.miniright.service;

import java.util.List;
import com.pknu26.miniright.dto.Comments;

public interface CommentsService {
    
    // 특정 게시글의 댓글 목록 가져오기
    List<Comments> getCommentsList(Long postId);

    // 댓글 작성
    void registerComment(Comments comments);

    // 댓글 수정
    void modifyComment(Comments comments);

    // 댓글 삭제
    void removeComment(Long commentId);
}