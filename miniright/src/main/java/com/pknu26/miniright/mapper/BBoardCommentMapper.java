package com.pknu26.miniright.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pknu26.miniright.dto.BBoardComment;

@Mapper
public interface BBoardCommentMapper {

    // 특정 거래게시글의 댓글 목록 조회
    List<BBoardComment> findByPostId(@Param("postId") Long postId);

    // 댓글 등록
    int insertComment(BBoardComment comment);

    // 댓글 삭제
    int deleteComment(@Param("commentId") Long commentId,
                      @Param("userId") Long userId);
}