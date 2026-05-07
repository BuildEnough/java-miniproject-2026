package com.pknu26.miniright.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.miniright.dto.Reply;
import com.pknu26.miniright.mapper.ReplyMapper;
import com.pknu26.miniright.validation.ReplyForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyMapper replyMapper;

    // 게시글 번호에 해당하는 답글 목록 조회
    @Override
    public List<Reply> getReplyListByPostId(Long postId) {
        return replyMapper.findByPostId(postId);
    }

    // 답글 한 건 조회
    @Override
    public Reply getReply(Long replyId) {
        return replyMapper.getReply(replyId);
    }

    // 답글 등록
    @Override
    public void createReply(ReplyForm replyForm) {

        Reply reply = new Reply();
        reply.setPostId(replyForm.getPostId());
        reply.setContents(replyForm.getContents());
        reply.setWriter(replyForm.getWriter());
        reply.setUserId(replyForm.getUserId());

        replyMapper.insertReply(reply);
    }

    // 답글 삭제
    @Override
    public void deleteReply(Long replyId) {
        replyMapper.deleteReply(replyId);
    }

}
