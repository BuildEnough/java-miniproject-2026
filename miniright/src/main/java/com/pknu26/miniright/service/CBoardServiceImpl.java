package com.pknu26.miniright.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.mapper.CBoardMapper;
import com.pknu26.miniright.validation.CBoardForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CBoardServiceImpl implements CBoardService{

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
        cBoard.setCContent(cBoardForm.getCContent());
        cBoard.setWriter(cBoardForm.getWriter()); 
        cBoard.setCreatedAt(LocalDateTime.now());

        // 게시글 DB 등록
        this.cBoardMapper.insertCBoard(cBoard);

    }

    // 게시글 하나씩 조회
    @Override
    public CBoard readCBoardById(Long cPostId) {
        this.cBoardMapper.increaseCViewCount(cPostId); // 조회수
        return this.cBoardMapper.findById(cPostId);
    }

    // 게시글 수정
    @Override
    public void updateCBoard(CBoardForm cBoardForm) {
        CBoard cBoard = new CBoard();

        cBoard.setCPostId(cBoardForm.getCPostId());
        cBoard.setTitle(cBoardForm.getTitle());
        cBoard.setCContent(cBoardForm.getCContent());
        cBoard.setWriter(cBoardForm.getWriter());
        cBoard.setUpdatedAt(LocalDateTime.now());

        // 수정 DB
        this.cBoardMapper.updateCBoard(cBoard);
    }

    // 게시글 삭제
    @Override
    public void deleteCBoard(Long cPostId) {
        this.cBoardMapper.deleteCBoard(cPostId);
    }

    // 수정 조회 메서드
    @Override
    public CBoard readCBoardForEdit(Long cPostId) {
        return this.cBoardMapper.findById(cPostId);
    }

}
