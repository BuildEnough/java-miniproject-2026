package com.pknu26.miniright.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pknu26.miniright.dto.Comments;
import com.pknu26.miniright.dto.LoginUser;
import com.pknu26.miniright.service.CommentsService;
import com.pknu26.miniright.validation.CommentsForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    // 댓글 등록 처리
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("commentsForm") CommentsForm commentsForm,
                         BindingResult bindingResult,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        // 로그인 체크
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        //유효성 검사 오류 처리 (내용이 비어있거나 글자수 초과 시)
        if (bindingResult.hasErrors()) {
            // 상세 페이지에서 에러 메시지를 보여주기 위해 bindingResult를 FlashAttribute에 담음
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentsForm", bindingResult);
            redirectAttributes.addFlashAttribute("commentsForm", commentsForm);
            redirectAttributes.addAttribute("skipViewCount", true);
            return "redirect:/bboard/detail/" + commentsForm.getPostId();
        }

        // Form 데이터를 DTO로 변환
        Comments comments = new Comments();
        comments.setPostId(commentsForm.getPostId());
        comments.setCategoryId(commentsForm.getCategoryId());
        comments.setContents(commentsForm.getContents());
        comments.setUserId(loginUser.getUserId());

        // 서비스 호출 (저장)
        this.commentsService.registerComment(comments);

        // 완료 후 상세 페이지로 리다이렉트 (조회수 증가 방지)
        redirectAttributes.addAttribute("skipViewCount", true);
        return "redirect:/bboard/detail/" + comments.getPostId();
    }

    // 댓글 수정 처리
    @PostMapping("/edit/{commentId}")
    public String edit(@PathVariable("commentId") Long commentId,
                       @RequestParam("postId") Long postId,
                       @RequestParam("contents") String contents,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        // 1. 로그인 체크
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // 2. DTO 담기 (내용과 아이디 세팅)
        Comments comments = new Comments();
        comments.setCommentId(commentId);
        comments.setContents(contents);
        
        // 3. 서비스 호출 (DB 업데이트)
        // commentsService에 modifyComment 또는 updateComment 메서드가 있어야 합니다.
        this.commentsService.modifyComment(comments);

        // 4. 원래 보던 상세 페이지로 리다이렉트
        redirectAttributes.addAttribute("skipViewCount", true);
        return "redirect:/bboard/detail/" + postId;
    }

    // 댓글 삭제 처리
    @PostMapping("/delete/{commentId}")
    public String delete(@PathVariable("commentId") Long commentId,
                         @RequestParam("postId") Long postId,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        // 로그인 체크
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // 삭제
        this.commentsService.removeComment(commentId);

        // 상세 페이지로 리다이렉트
        redirectAttributes.addAttribute("skipViewCount", true);
        return "redirect:/bboard/detail/" + postId;
    }
}