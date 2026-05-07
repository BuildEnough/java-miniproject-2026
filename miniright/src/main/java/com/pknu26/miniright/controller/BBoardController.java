package com.pknu26.miniright.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pknu26.miniright.dto.BBoard;
import com.pknu26.miniright.dto.LoginUser;
import com.pknu26.miniright.dto.PageRequest;
import com.pknu26.miniright.dto.PageResponse;
import com.pknu26.miniright.service.BBoardService;
import com.pknu26.miniright.validation.BBoardForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bboard")
@RequiredArgsConstructor
public class BBoardController {

    private final BBoardService bBoardService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 게시글 목록 조회 + 검색 + 페이징
    @GetMapping("/list")
    public String list(@ModelAttribute PageRequest pageRequest, Model model) {
        List<BBoard> bBoardList = this.bBoardService.readBBoardList(pageRequest);

        int totalCount = this.bBoardService.getTotalCount(pageRequest);
        int currentPage = pageRequest.getPage();
        int size = pageRequest.getSize();

        PageResponse<BBoard> pageResponse =
                new PageResponse<>(bBoardList, totalCount, currentPage, size);

        model.addAttribute("bBoardList", pageResponse);
        model.addAttribute("pageRequest", pageRequest);

        return "bboard/list";
    }

    // 등록 화면
    @GetMapping("/create")
    public String registerForm(Model model, HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        model.addAttribute("bBoardForm", new BBoardForm());

        return "bboard/form";
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
            return "bboard/form";
        }

        try {
            String imagePath = saveImage(bBoardForm.getImageFile());
            bBoardForm.setImagePath(imagePath);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("imageFile", "imageFile", e.getMessage());
            return "bboard/form";
        }

        bBoardForm.setUserId(loginUser.getUserId());

        this.bBoardService.createBBoard(bBoardForm);

        return "redirect:/bboard/list";
    }

    // 상세 조회
    // 일반 상세보기: 조회수 증가 O
    // 수정 후 상세보기 이동: skipViewCount=true면 조회수 증가 X
    @GetMapping("/detail/{postId}")
    public String detail(@PathVariable("postId") Long postId,
                         @RequestParam(value = "skipViewCount", defaultValue = "false") boolean skipViewCount,
                         Model model,
                         HttpSession session) {

        BBoard bBoard;

        if (skipViewCount) {
            // 수정 후 상세 페이지로 돌아온 경우 조회수 증가 X
            bBoard = this.bBoardService.readBBoardForEdit(postId);
        } else {
            // 일반 상세보기는 조회수 증가 O
            bBoard = this.bBoardService.readBBoardById(postId);
        }

        if (bBoard == null) {
            return "redirect:/bboard/list";
        }

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        boolean isOwner = false;

        if (loginUser != null && bBoard.getUserId() != null) {
            isOwner = loginUser.getUserId().equals(bBoard.getUserId());
        }

        model.addAttribute("bboard", bBoard);
        model.addAttribute("isOwner", isOwner);

        return "bboard/detail";
    }

    // 수정 화면: 조회수 증가 X
    @GetMapping("/edit/{postId}")
    public String showEditForm(@PathVariable("postId") Long postId,
                               Model model,
                               HttpSession session) {

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // 수정 화면에서는 조회수 증가하면 안 되므로 readBBoardForEdit 사용
        BBoard bBoard = this.bBoardService.readBBoardForEdit(postId);

        if (bBoard == null) {
            return "redirect:/bboard/list";
        }

        // 작성자 본인만 수정 가능
        if (!loginUser.getUserId().equals(bBoard.getUserId())) {
            return "redirect:/bboard/detail/" + postId;
        }

        BBoardForm bBoardForm = new BBoardForm();
        bBoardForm.setPostId(bBoard.getPostId());
        bBoardForm.setCategoryId(bBoard.getCategoryId());
        bBoardForm.setTitle(bBoard.getTitle());
        bBoardForm.setContents(bBoard.getContents());
        bBoardForm.setImagePath(bBoard.getImagePath());

        model.addAttribute("bBoardForm", bBoardForm);

        return "bboard/form";
    }

    // 수정 처리: 조회수 증가 X
    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId,
                       @Valid @ModelAttribute("bBoardForm") BBoardForm bBoardForm,
                       BindingResult bindingResult,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // 수정 처리에서도 조회수 증가하면 안 되므로 readBBoardForEdit 사용
        BBoard originBoard = this.bBoardService.readBBoardForEdit(postId);

        if (originBoard == null) {
            return "redirect:/bboard/list";
        }

        // 작성자 본인만 수정 가능
        if (!loginUser.getUserId().equals(originBoard.getUserId())) {
            return "redirect:/bboard/detail/" + postId;
        }

        if (bindingResult.hasErrors()) {
            return "bboard/form";
        }

        try {
            String newImagePath = saveImage(bBoardForm.getImageFile());

            // 새 이미지가 있으면 새 이미지로 교체
            if (newImagePath != null) {
                bBoardForm.setImagePath(newImagePath);
            }

            // 새 이미지가 없으면 hidden input으로 넘어온 기존 imagePath 유지
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("imageFile", "imageFile", e.getMessage());
            return "bboard/form";
        }

        bBoardForm.setPostId(postId);

        this.bBoardService.updateBBoard(bBoardForm);

        // 수정 후 상세 페이지로 이동할 때 조회수 증가 막기
        // /bboard/detail/번호?skipViewCount=true 형태로 이동
        redirectAttributes.addAttribute("skipViewCount", true);

        return "redirect:/bboard/detail/" + postId;
    }

    // 삭제 처리: 조회수 증가 X
    @PostMapping("/delete/{postId}")
    public String delete(@PathVariable("postId") Long postId,
                         HttpSession session) {

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // 삭제 권한 확인에서도 조회수 증가하면 안 되므로 readBBoardForEdit 사용
        BBoard bBoard = this.bBoardService.readBBoardForEdit(postId);

        if (bBoard == null) {
            return "redirect:/bboard/list";
        }

        // 작성자 본인만 삭제 가능
        if (!loginUser.getUserId().equals(bBoard.getUserId())) {
            return "redirect:/bboard/detail/" + postId;
        }

        this.bBoardService.deleteBBoard(postId);

        return "redirect:/bboard/list";
    }

    // 이미지 저장
    private String saveImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        String contentType = imageFile.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }

        String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());

        String extension = "";

        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex != -1) {
            extension = originalFilename.substring(dotIndex);
        }

        String savedFilename = UUID.randomUUID() + extension;

        try {
            Path uploadPath = Paths.get(uploadDir, "bboard")
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(uploadPath);

            Path targetPath = uploadPath.resolve(savedFilename);

            System.out.println("이미지 저장 위치: " + targetPath);

            imageFile.transferTo(targetPath.toFile());

            return "/uploads/bboard/" + savedFilename;
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 저장 중 오류가 발생했습니다.");
        }
    }
}