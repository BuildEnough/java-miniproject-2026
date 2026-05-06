package com.pknu26.miniright.service;

import java.util.List;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.validation.CBoardForm;

public interface CBoardService {

    List<CBoard> readCBoardList();

    // Create(Insert)
    void createCBoard(CBoardForm cBoardForm);

    // Read(Select)
    CBoard readCBoardById(Long PostId);

    // Update
    void updateCBoard(CBoardForm cBoardForm);

    // Delete
    void deleteCBoard(Long PostId);

    // 수정 조회 메서드
    CBoard readCBoardForEdit(Long PostId);

}
