package com.pknu26.miniright.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pknu26.miniright.dto.Comments;
import com.pknu26.miniright.dto.LoginUser;
import com.pknu26.miniright.service.CommentsService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    // 댓글 등록 처리
    @PostMapping("/create")
    public String create(Comments comments, HttpSession session, RedirectAttributes redirectAttributes) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        // 로그인 확인
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // 세션에서 로그인한 사용자의 ID 설정
        comments.setUserId(loginUser.getUserId());

        // 댓글 저장
        this.commentsService.registerComment(comments);

        // 상세 페이지로 다시 이동 (조회수 증가 방지 파라미터 포함)
        redirectAttributes.addAttribute("skipViewCount", true);
        return "redirect:/bboard/detail/" + comments.getPostId();
    }

    // 댓글 삭제 처리
    @PostMapping("/delete/{commentId}")
    public String delete(@PathVariable("commentId") Long commentId, 
                         Long postId, // 상세 페이지로 돌아가기 위해 postId 필요
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // 실제 운영 시에는 여기서 작성자 본인인지 확인하는 로직이 서비스나 컨트롤러에 추가되어야 합니다.
        this.commentsService.removeComment(commentId);

        // 상세 페이지로 다시 이동
        redirectAttributes.addAttribute("skipViewCount", true);
        return "redirect:/bboard/detail/" + postId;
    }
}