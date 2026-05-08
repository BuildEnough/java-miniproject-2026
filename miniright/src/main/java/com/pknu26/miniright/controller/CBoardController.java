package com.pknu26.miniright.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.dto.LoginUser;
import com.pknu26.miniright.dto.PageRequest;
import com.pknu26.miniright.dto.PageResponse;
import com.pknu26.miniright.service.CBoardService;
import com.pknu26.miniright.service.ReplyService;
import com.pknu26.miniright.validation.CBoardForm;
import com.pknu26.miniright.validation.ReplyForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cboard")
public class CBoardController {

    @Autowired
    private CBoardService cBoardService;

    // 댓글 조회
    @Autowired
    private ReplyService replyService;


    // 전체 게시글 목록 조회
    @GetMapping("/list")
    public String list(@ModelAttribute PageRequest pageRequest, Model model) {

        List<CBoard> cBoardList = this.cBoardService.readCBoardList(pageRequest);
        
        // 전체 게시글 개수 조회
        int totalCount = this.cBoardService.countAll();

        PageResponse<CBoard> pageResponse = new PageResponse<>(
                cBoardList,
                totalCount,
                pageRequest.getPage(),
                pageRequest.getSize()
        );

        // 화면 전달
        model.addAttribute("cBoardList", cBoardList);
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("pageRequest", pageRequest);

        return "cboard/list";
    }

    // 글쓰기 GET -- 비로그인 시 로그인 화면으로 넘어가는 기능 GET에 추가
    @GetMapping("/create")
    public String showCreateForm( @ModelAttribute("cBoardForm") CBoardForm cBoardForm, HttpSession session) {

        // 사용자 로그인 정보
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        cBoardForm.setWriter(loginUser.getName()); // 작성자 이름을 로그인 아이디 이름으로 입력
        cBoardForm.setWriterId(loginUser.getLoginId()); // 작성자 아이디를 로그인 아이디로 저장
 
        return "cboard/form";
    }
        
    // 글쓰기 POST -- 유효성 검사 추가
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("cBoardForm") CBoardForm cBoardForm,
                        BindingResult bindingResult,
                        Model model,
                        HttpSession session) {
        
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        
        // 입력 오류 시 작성 화면으로 
        if (bindingResult.hasErrors()) {
            return "cboard/form";
        }

        // 작성자 익명 처리
        if (cBoardForm.getAnonymous() == null) { // 익명 체크 안했으면 로그인된 작성자 이름으로 처리
            cBoardForm.setAnonymous(0);
        }

        this.cBoardService.createCBoard(cBoardForm, loginUser.getUserId());

        return "redirect:/cboard/list";
    }

    // 게시글 상세보기
    @GetMapping("/detail/{postId}")
    public String detail(@PathVariable("postId") Long postId, Model model, HttpSession session) {

        // 게시글 조회 후 조회수 증가 X
        Boolean skipViewCount = (Boolean) model.asMap().get("skipViewCount");

        CBoard cBoard;

        if(Boolean.TRUE.equals(skipViewCount)){
            cBoard = this.cBoardService.readCBoardForEdit(postId);
        } else {
            cBoard = this.cBoardService.readCBoardById(postId);
        }

        model.addAttribute("cBoard", cBoard);
        model.addAttribute("replyList", this.replyService.getReplyListByPostId(postId));

        // 댓글 작성폼
        if (!model.containsAttribute("replyForm")) {
            ReplyForm replyForm = new ReplyForm(); // 작성 폼이 없으면 새로 생성
            replyForm.setPostId(postId);

            LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
            if (loginUser != null) {
                replyForm.setWriter(loginUser.getName());
            }

            model.addAttribute("replyForm", replyForm);
        }
        
        return "cboard/detail";
    }

    // 글 수정 GET
    @GetMapping("/edit/{postId}")
    public String showEditForm(@PathVariable("postId") Long postId, Model model, HttpSession session) {
        
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        CBoard cBoard = this.cBoardService.readCBoardForEdit(postId);
        
        // 본인 글인지 검사, 본인 글 아니면 수정 불가
        if (!cBoard.getUserId().equals(loginUser.getUserId())) {
            return "redirect:/cboard/detail/" + postId;
        }

        CBoardForm cBoardForm = new CBoardForm();
        cBoardForm.setPostId(cBoard.getPostId());
        cBoardForm.setTitle(cBoard.getTitle());
        cBoardForm.setContents(cBoard.getContents());
        cBoardForm.setWriter(cBoard.getWriter());

        model.addAttribute("cBoardForm", cBoardForm);
        return "cboard/form";
    }

    // 수정 POST
    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId,
                       @Valid @ModelAttribute("cBoardForm") CBoardForm cBoardForm,
                       BindingResult bindingResult, HttpSession session,
                       RedirectAttributes redirectAttributes) {
        
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        
        CBoard cBoard = this.cBoardService.readCBoardForEdit(postId);
        
        // 본인 글 인지 검사
        if (!cBoard.getUserId().equals(loginUser.getUserId())) {
            return "redirect:/cboard/detail/" + postId;
        }

        if (bindingResult.hasErrors()) {
            return "cboard/form";
        }

        cBoardForm.setPostId(postId);
        this.cBoardService.updateCBoard(cBoardForm);

        // 수정 후 상세페이지로 이동 시 게시글 조회 X
        redirectAttributes.addFlashAttribute("skipViewCount", true);

        return "redirect:/cboard/detail/" + postId;
    }

    // 삭제
    @PostMapping("/delete/{postId}")
    public String delete(@PathVariable("postId") Long postId, HttpSession session) {
        
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        CBoard cBoard = cBoardService.getCBoard(postId);

        // 게시글 존재 여부 -- 게시글이 사라졌으면 cboard/list.html로 돌아감
        if (cBoard == null) {
            return "redirect:/cboard/list";
        }

        if (!cBoard.getUserId().equals(loginUser.getUserId())) {
            return "redirect:/cboard/list";
        }

        this.cBoardService.deleteCBoard(postId);

        return "redirect:/cboard/list";
    }

}
