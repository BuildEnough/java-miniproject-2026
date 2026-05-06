package com.pknu26.miniright.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.service.CBoardService;
import com.pknu26.miniright.validation.CBoardForm;

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

        return "cboard/list";
    }

    // 글쓰기 GET
    @GetMapping("/create")
    public String showCreateForm(@ModelAttribute("cBoardForm") CBoardForm cBoardForm) {
        return "cboard/form";
    }
    
    // 글쓰기 POST
    @PostMapping("/create")
    public String create(@Valid CBoardForm cBoardForm, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return "cboard/form";
        }

        this.cBoardService.createCBoard(cBoardForm);

        return "redirect:/cboard/list";
    }

    // 글 상세보기
    @GetMapping("/detail/{cPostId}")
    public String detail(@PathVariable("cPostId") Long cPostId, Model model) {
        CBoard cBoard = this.cBoardService.readCBoardById(cPostId);

        model.addAttribute("cBoard", cBoard);
        
        return "cboard/detail";
    }

    // 글 수정 GET
    @GetMapping("/edit/{cPostId}")
    public String showEditForm(@PathVariable("cPostId") Long cPostId, Model model) {
        // CBoard cBoard = this.cBoardService.readCBoardById(cPostId);
        CBoard cBoard = this.cBoardService.readCBoardForEdit(cPostId);

        CBoardForm cBoardForm = new CBoardForm();
        cBoardForm.setCPostId(cBoard.getPostId());
        cBoardForm.setTitle(cBoard.getTitle());
        cBoardForm.setCContent(cBoard.getContent());
        cBoardForm.setWriter(cBoard.getWriter());

        model.addAttribute("cBoardForm", cBoardForm);
        return "/cboard/form";
    }

    // 수정 POST
    @PostMapping("/edit/{cPostId}")
    public String edit(@PathVariable("cPostId") Long cPostId,
                       @Valid @ModelAttribute("cBoardForm") CBoardForm cBoardForm,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/cboard/form";
        }

        cBoardForm.setCPostId(cPostId);
        this.cBoardService.updateCBoard(cBoardForm);
        return "redirect:/cboard/detail/" + cPostId;
    }

    // 삭제
    @PostMapping("/delete/{cPostId}")
    public String delete(@PathVariable("cPostId") Long cPostId) {
        this.cBoardService.deleteCBoard(cPostId);
        
        return "redirect:/cboard/list";
    }

}
