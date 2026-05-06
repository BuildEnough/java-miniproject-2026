package com.pknu26.miniright.service;

import java.util.List;

import com.pknu26.miniright.dto.BBoard;
import com.pknu26.miniright.dto.PageRequest; // ★ dto 패키지 임포트 확인
import com.pknu26.miniright.validation.BBoardForm;

public interface BBoardService {
    
    // 이 메서드 선언이 있는지 확인하세요
    List<BBoard> readBBoardList(PageRequest pageRequest);
    
    BBoard readBBoardById(Long bPostId);
    
    void createBBoard(BBoardForm bBoardForm);
    void updateBBoard(BBoardForm bBoardForm);
    void deleteBBoard(Long bPostId);
    
    int getTotalCount();
}