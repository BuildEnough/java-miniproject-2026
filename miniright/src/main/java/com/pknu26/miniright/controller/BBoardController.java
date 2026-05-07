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
import com.pknu26.miniright.dto.LoginUser;
import com.pknu26.miniright.dto.PageRequest;
import com.pknu26.miniright.dto.PageResponse;
import com.pknu26.miniright.service.BBoardService;
import com.pknu26.miniright.validation.BBoardForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/bboard")
public class BBoardController {

    @Autowired
    private BBoardService bBoardService;

    // 게시글 목록 조회
    @GetMapping("/list")
    public String list(@ModelAttribute PageRequest pageRequest, Model model) {
        List<BBoard> bBoardList = this.bBoardService.readBBoardList(pageRequest);

        int totalCount = this.bBoardService.getTotalCount();
        int currentPage = pageRequest.getPage();
        int size = pageRequest.getSize();

        PageResponse<BBoard> pageResponse =
                new PageResponse<>(bBoardList, totalCount, currentPage, size);

        model.addAttribute("bBoardList", pageResponse);

        return "/bboard/list";
    }

    // 등록 화면
    @GetMapping("/create")
    public String registerForm(Model model, HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        model.addAttribute("bBoardForm", new BBoardForm());
        return "/bboard/form";
    }

    // 등록 처리
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("bBoardForm") BBoardForm bBoardForm,
                         BindingResult bindingResult,
                         Model model,
                         HttpSession session) {

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("bBoardForm", bBoardForm);
            return "/bboard/form";
        }

        bBoardForm.setUserId(loginUser.getUserId());

        this.bBoardService.createBBoard(bBoardForm);

        return "redirect:/bboard/list";
    }

    // 상세 조회
    @GetMapping("/detail/{postId}")
    public String detail(@PathVariable("postId") Long postId, Model model) {
        BBoard bBoard = this.bBoardService.readBBoardById(postId);

        model.addAttribute("bboard", bBoard);

        return "/bboard/detail";
    }

    // 수정 화면
    @GetMapping("/edit/{postId}")
    public String showEditForm(@PathVariable("postId") Long postId,
                               Model model,
                               HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        BBoard bBoard = this.bBoardService.readBBoardById(postId);

        BBoardForm bBoardForm = new BBoardForm();
        bBoardForm.setPostId(bBoard.getPostId());
        bBoardForm.setCategoryId(bBoard.getCategoryId());
        bBoardForm.setTitle(bBoard.getTitle());
        bBoardForm.setContents(bBoard.getContents());

        model.addAttribute("bBoardForm", bBoardForm);

        return "/bboard/form";
    }

    // 수정 처리
    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId,
                       @Valid @ModelAttribute("bBoardForm") BBoardForm bBoardForm,
                       BindingResult bindingResult,
                       HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        if (bindingResult.hasErrors()) {
            return "/bboard/form";
        }

        bBoardForm.setPostId(postId);
        this.bBoardService.updateBBoard(bBoardForm);

        return "redirect:/bboard/detail/" + postId;
    }

    // 삭제
    @PostMapping("/delete/{postId}")
    public String delete(@PathVariable("postId") Long postId,
                         HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        this.bBoardService.deleteBBoard(postId);

        return "redirect:/bboard/list";
    }
}