package com.pknu26.miniright.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.mapper.CBoardMapper;
import com.pknu26.miniright.validation.CBoardForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CBoardServiceImpl implements CBoardService {

    private final CBoardMapper cBoardMapper;

    // 게시글 전체 목록 조회
    @Override
    public List<CBoard> readCBoardList() {
        return this.cBoardMapper.findAll();
    }

    // 게시글 등록
    @Override
    public void createCBoard(CBoardForm cBoardForm) {
        CBoard cBoard = new CBoard();
        cBoard.setTitle(cBoardForm.getTitle());
        cBoard.setContent(cBoardForm.getCContent()); // 필드명 일치
        cBoard.setWriter(cBoardForm.getWriter());

        // 게시글 DB 등록
        this.cBoardMapper.insertCBoard(cBoard);
    }

    // 게시글 하나씩 조회
    @Override
    public CBoard readCBoardById(Long postId) {
        this.cBoardMapper.increaseViewCount(postId); // 메서드명 일치
        return this.cBoardMapper.findById(postId);
    }

    // 게시글 수정
    @Override
    public void updateCBoard(CBoardForm cBoardForm) {
        CBoard cBoard = new CBoard();
        cBoard.setPostId(cBoardForm.getCPostId()); // 필드명 일치
        cBoard.setTitle(cBoardForm.getTitle());
        cBoard.setContent(cBoardForm.getCContent());
        cBoard.setWriter(cBoardForm.getWriter());

        // 수정 DB
        this.cBoardMapper.updateCBoard(cBoard);
    }

    // 게시글 삭제
    @Override
    public void deleteCBoard(Long postId) {
        this.cBoardMapper.deleteCBoard(postId);
    }

    // 수정 조회 메서드
    @Override
    public CBoard readCBoardForEdit(Long postId) {
        return this.cBoardMapper.findById(postId);
    }
}
