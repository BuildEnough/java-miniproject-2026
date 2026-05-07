package com.pknu26.miniright.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.miniright.dto.Reply;

@Mapper
public interface ReplyMapper {

    // 전체 답글 조회
    List<Reply> findByPostId(Long PostId);

    // 답글 하나 조회
    Reply getReply(Long replyId);

    // 답글 등록(Create)
    int insertReply(Reply reply);

    // 답글 수정
    int updateReply(Reply reply);

    // 답글 삭제
    int deleteReply(Long replyId);

}
