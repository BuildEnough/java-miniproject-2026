package com.pknu26.miniright.service;

import java.util.List;

import com.pknu26.miniright.dto.BBoard;
import com.pknu26.miniright.dto.PageRequest;
import com.pknu26.miniright.validation.BBoardForm;

public interface BBoardService {

    List<BBoard> readBBoardList(PageRequest pageRequest);

    int getTotalCount(PageRequest pageRequest);

    BBoard readBBoardById(Long postId);

    void createBBoard(BBoardForm bBoardForm);

    void updateBBoard(BBoardForm bBoardForm);

    void deleteBBoard(Long postId);
}