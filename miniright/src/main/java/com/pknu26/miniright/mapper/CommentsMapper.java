package com.pknu26.miniright.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.pknu26.miniright.dto.Comments;

@Mapper
public interface CommentsMapper {
    // 게시글 번호에 해당하는 댓글 목록 조회
    List<Comments> selectCommentsList(@Param("postId") Long postId);

    // 댓글 저장
    int insertComment(Comments comments);

    // 댓글 수정
    int updateComment(Comments comments);

    // 댓글 삭제
    int deleteComment(@Param("commentId") Long commentId);
}