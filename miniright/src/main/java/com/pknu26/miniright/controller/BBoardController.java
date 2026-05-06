package com.pknu26.miniright.controller;

import java.util.List;

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
import com.pknu26.miniright.dto.PageRequest;
import com.pknu26.miniright.dto.PageResponse;
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
    public String list(@ModelAttribute PageRequest pageRequest, Model model) {
        List<BBoard> bBoardList = (List<BBoard>) this.bBoardService.readBBoardList(pageRequest);
        
        int totalCount = this.bBoardService.getTotalCount(); 
        int currentPage = pageRequest.getPage(); 
        int size = pageRequest.getSize(); 
        
        PageResponse<BBoard> pageResponse = new PageResponse<>(bBoardList, totalCount, currentPage, size);
        
        // 속성 이름 주의 (list.html에서 어떻게 받는지 확인)
        model.addAttribute("bBoardList", pageResponse); 

        return "bboard/list";
    }

    // Create와 Register 통합
    @GetMapping({"/create", "/register"})
    public String registerForm(Model model) {
        model.addAttribute("bboardForm", new BBoardForm());
        return "bboard/form"; 
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("bBoardForm") BBoardForm bBoardForm,
                         BindingResult bindingResult,
                         Model model) {
        

        bBoardForm.setUserId(1L);

        if (bindingResult.hasErrors()) {
            model.addAttribute("bBoardForm", bBoardForm); 
            return "/bboard/form"; 
        }
        this.bBoardService.createBBoard(bBoardForm);
        return "redirect:/bboard/list";
    }

    @GetMapping("/detail/{postId}")
    public String detail(@PathVariable("postId") Long postId, Model model) {
        model.addAttribute("BBoard", this.bBoardService.readBBoardById(postId));
        return "bboard/detail"; 
    }

    @GetMapping("/edit/{postId}")
    public String showEditForm(@PathVariable("bPostId") Long postId, Model model) {
        BBoard bBoard = this.bBoardService.readBBoardById(postId);
        BBoardForm bBoardForm = new BBoardForm();
        
        // DTO 필드명 매칭
        bBoardForm.setPostId(bBoard.getPostId()); 
        bBoardForm.setTitle(bBoard.getTitle());
        bBoardForm.setContents(bBoard.getContents());
        
        model.addAttribute("bBoardForm", bBoardForm);
        return "bboard/form";  
    }

    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId,
                    @Valid @ModelAttribute("bBoardForm") BBoardForm bBoardForm,
                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "bboard/form"; 
        }
        bBoardForm.setPostId(postId);
        this.bBoardService.updateBBoard(bBoardForm);
        return "redirect:/bboard/detail/" + postId;
    }

    @PostMapping("/delete/{postId}")
    public String delete(@PathVariable("postId") Long postId) {
        this.bBoardService.deleteBBoard(postId);
        return "redirect:/bboard/list";
    }
}