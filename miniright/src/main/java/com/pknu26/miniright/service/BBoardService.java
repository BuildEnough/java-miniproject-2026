package com.pknu26.miniright.service;

import java.util.List;

import com.pknu26.miniright.dto.BBoard;
import com.pknu26.miniright.validation.BBoardForm;

public interface BBoardService {

    List<BBoard> readBBoardList();

    // Create(Insert)
    void createBBoard(BBoardForm bBoardForm);

    // Read(Select)
    BBoard readBBoardById(Long bPostId);

    // Update
    void updateBBoard(BBoardForm bBoardForm);

    // Delete
    void deleteBBoard(Long bPostId);
}
