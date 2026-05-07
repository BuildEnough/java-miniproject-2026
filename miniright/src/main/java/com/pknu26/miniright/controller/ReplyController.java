package com.pknu26.miniright.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.dto.LoginUser;
import com.pknu26.miniright.dto.Reply;
import com.pknu26.miniright.service.CBoardService;
import com.pknu26.miniright.service.ReplyService;
import com.pknu26.miniright.validation.ReplyForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final CBoardService cBoardService;

    // 답글 쓰기 POST
    @PostMapping("/create")
    public String createReply(@Valid @ModelAttribute("replyForm") ReplyForm replyForm,
                              BindingResult bindingResult,
                              Model model,
                              HttpSession session){

        // 로그인 세션 
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if(loginUser == null) {
            return "redirect:/user/login"; 
        }
        
        // 로그인한 사용자의 userId를 댓글 form에 넣어줌
        replyForm.setUserId(loginUser.getUserId());

        // 유효성 검사
        if (bindingResult.hasErrors()){
            CBoard cBoard = this.cBoardService.readCBoardForEdit(replyForm.getPostId());
            model.addAttribute("cBoard", cBoard);
            model.addAttribute("replyList", this.replyService.getReplyListByPostId(replyForm.getPostId()));
            return "cboard/detail";
        }
        
        this.replyService.createReply(replyForm);

        return "redirect:/cboard/detail/" + replyForm.getPostId();
    }

    //삭제
    @PostMapping("/delete/{replyId}")
    public String deleteReply(@PathVariable("replyId") Long replyId,
                              @RequestParam Long postId, HttpSession session) {
        
        // 로그인 세션 추가
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if(loginUser == null) {
            return "redirect:/user/login";
        }

        Reply reply = replyService.getReply(replyId);

        if (reply == null) {
            return "redirect:/cboard/detail/" + postId;
        }

        if(!reply.getUserId().equals(loginUser.getUserId())) {
            return "redirect:/cboard/detail/" + postId;
        }

        this.replyService.deleteReply(replyId);
        return "redirect:/cboard/detail/" + postId;
    }
}
