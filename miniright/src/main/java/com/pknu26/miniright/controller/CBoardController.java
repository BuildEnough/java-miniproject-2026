package com.pknu26.miniright.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.dto.LoginUser;
import com.pknu26.miniright.service.CBoardService;
import com.pknu26.miniright.validation.CBoardForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cboard")
public class CBoardController {

    @Autowired
    private CBoardService cBoardService;

    // 게시글 목록 조회
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("cBoardList", this.cBoardService.readCBoardList());

        return "/cboard/list";
    }

    // 글쓰기 GET
    @GetMapping("/create")
    public String showCreateForm(@ModelAttribute("cBoardForm") CBoardForm cBoardForm) {
        return "/cboard/form";
    }
    
    // 글쓰기 POST
    @PostMapping("/create")
    public String create(@Valid CBoardForm cBoardForm, BindingResult bindingResult, HttpSession session) {
        
        if (bindingResult.hasErrors()) {
            return "/cboard/form";
        }

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        this.cBoardService.createCBoard(cBoardForm, loginUser.getUserId());

        return "redirect:/cboard/list";
    }

    // 글 상세보기
    @GetMapping("/detail/{postId}")
    public String detail(@PathVariable("postId") Long postId, Model model) {
        CBoard cBoard = this.cBoardService.readCBoardById(postId);

        model.addAttribute("cBoard", cBoard);
        
        return "/cboard/detail";
    }

    // 글 수정 GET
    @GetMapping("/edit/{postId}")
    public String showEditForm(@PathVariable("postId") Long postId, Model model) {
        // CBoard cBoard = this.cBoardService.readCBoardById(cPostId);
        CBoard cBoard = this.cBoardService.readCBoardForEdit(postId);

        CBoardForm cBoardForm = new CBoardForm();
        cBoardForm.setPostId(cBoard.getPostId());
        cBoardForm.setTitle(cBoard.getTitle());
        cBoardForm.setContents(cBoard.getContents());
        cBoardForm.setWriter(cBoard.getWriter());

        model.addAttribute("cBoardForm", cBoardForm);
        return "/cboard/form";
    }

    // 수정 POST
    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId,
                       @Valid @ModelAttribute("cBoardForm") CBoardForm cBoardForm,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/cboard/form";
        }

        cBoardForm.setPostId(postId);
        this.cBoardService.updateCBoard(cBoardForm);
        return "redirect:/cboard/detail/" + postId;
    }

    // 삭제
    @PostMapping("/delete/{postId}")
    public String delete(@PathVariable("postId") Long postId) {
        this.cBoardService.deleteCBoard(postId);
        
        return "redirect:/cboard/list";
    }

}
