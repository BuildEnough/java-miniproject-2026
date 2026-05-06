package com.pknu26.miniright.service;

import java.util.List;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.validation.CBoardForm;

public interface CBoardService {

    List<CBoard> readCBoardList();

    // Create(Insert)
    void createCBoard(CBoardForm cBoardForm, Long userId);

    // Read(Select)
    CBoard readCBoardById(Long postId);

    // Update
    void updateCBoard(CBoardForm cBoardForm);

    // Delete
    void deleteCBoard(Long postId);

    // 수정 조회 메서드
    CBoard readCBoardForEdit(Long postId);

}
