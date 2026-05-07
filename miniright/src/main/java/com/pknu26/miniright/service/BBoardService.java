package com.pknu26.miniright.service;

import java.util.List;

import com.pknu26.miniright.dto.BBoard;
import com.pknu26.miniright.dto.PageRequest;
import com.pknu26.miniright.validation.BBoardForm;

public interface BBoardService {

    List<BBoard> readBBoardList(PageRequest pageRequest);

    int getTotalCount(PageRequest pageRequest);

    // 상세보기용: 조회수 증가 O
    BBoard readBBoardById(Long postId);

    // 수정/삭제 권한 확인용: 조회수 증가 X
    BBoard readBBoardForEdit(Long postId);

    void createBBoard(BBoardForm bBoardForm);

    void updateBBoard(BBoardForm bBoardForm);

    void deleteBBoard(Long postId);
}