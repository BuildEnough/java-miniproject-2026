package com.pknu26.miniright.service;

import java.util.List;

import com.pknu26.miniright.dto.Reply;
import com.pknu26.miniright.validation.ReplyForm;

public interface ReplyService {

    // 답글 전체 조회 리스트
    List<Reply> getReplyListByPostId(Long postId);

    // 답글 하나 조회
    Reply getReply(Long replyId);

    // 답글 작성
    void createReply(ReplyForm replyform);

    // 답글 삭제
    void deleteReply(Long replyId);
    
}
