package com.pknu26.miniright.service;

import java.util.List;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.dto.PageRequest;
import com.pknu26.miniright.validation.CBoardForm;

public interface CBoardService {

    // 게시글 목록 조회 + 페이징
    List<CBoard> readCBoardList(PageRequest pageRequest);

    // 페이징용 전체 게시글 개수 조회
    int countAll();

    // 게시글 한 건 조회
    CBoard getCBoard(Long postId);

    // 게시글 등록 Create(Insert)
    void createCBoard(CBoardForm cBoardForm, Long userId);

    // 게시글 조회 Read(Select)
    CBoard readCBoardById(Long postId);

    // 게시글 수정 Update
    void updateCBoard(CBoardForm cBoardForm);

    // 게시글 수정 조회 메서드
    CBoard readCBoardForEdit(Long postId);
    
    // 게시글 삭제 Delete
    void deleteCBoard(Long postId);

}
