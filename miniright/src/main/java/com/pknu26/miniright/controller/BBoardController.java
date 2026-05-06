package com.pknu26.miniright.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.miniright.dto.BBoard;
import com.pknu26.miniright.service.BBoardService;
import com.pknu26.miniright.validation.BBoardForm;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/bboard")
public class BBoardController {

    @Autowired
    private BBoardService bBoardService;

    // 게시글 목록 조회
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("bBoardList", this.bBoardService.readBBoardList());
        
        return "/bboard/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("bBoardForm", new BBoardForm());
        return "/bboard/form"; 
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("bBoardForm") BBoardForm bBoardForm,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bBoardForm", bBoardForm); 
            return "/bboard/form"; 
        }
        this.bBoardService.createBBoard(bBoardForm);
        return "redirect:/bboard/list";
    }

    @GetMapping("/detail/{bPostId}")
    public String detail(@PathVariable("bPostId") Long bPostId, Model model) {
        model.addAttribute("BBoard", this.bBoardService.readBBoardById(bPostId));
        return "/bboard/detail"; 
    }

    @GetMapping("/edit/{bPostId}")
    public String showEditForm(@PathVariable("bPostId") Long bPostId, Model model) {
        BBoard bBoard = this.bBoardService.readBBoardById(bPostId);
        BBoardForm bBoardForm = new BBoardForm();
        bBoardForm.setBPostId(bBoard.getBPostId());
        bBoardForm.setTitle(bBoard.getTitle());
        bBoardForm.setBContent(bBoard.getBContent());
        model.addAttribute("bBoardForm", bBoardForm);
        return "/bboard/form";  
    }

    @PostMapping("/edit/{bPostId}")
    public String edit(@PathVariable("bPostId") Long bPostId,
                    @Valid @ModelAttribute("bBoardForm") BBoardForm bBoardForm,
                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/bboard/form"; 
        }
        bBoardForm.setBPostId(bPostId);
        this.bBoardService.updateBBoard(bBoardForm);
        return "redirect:/bboard/detail/" + bPostId;
    }

    @PostMapping("/delete/{bPostId}")
    public String delete(@PathVariable("bPostId") Long bPostId) {
        this.bBoardService.deleteBBoard(bPostId);
        return "redirect:/bboard/list";
    }
}
