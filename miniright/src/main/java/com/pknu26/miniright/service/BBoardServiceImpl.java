package com.pknu26.miniright.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.miniright.dto.BBoard;
import com.pknu26.miniright.mapper.BBoardMapper;
import com.pknu26.miniright.validation.BBoardForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BBoardServiceImpl implements BBoardService{

    private final BBoardMapper bBoardMapper;

    // 게시글 전체 목록 조회
    @Override
    public List<BBoard> readBBoardList() {
        return this.bBoardMapper.findAll();
    }

    // 게시글 등록
    @Override
    public void createBBoard(BBoardForm bBoardForm) {

        BBoard bBoard = new BBoard();

        bBoard.setTitle(bBoardForm.getTitle()); 
        bBoard.setBContent(bBoardForm.getBContent());
        bBoard.setCreatedAt(LocalDateTime.now());

        // 게시글 DB 등록
        this.bBoardMapper.insertBBoard(bBoard);

    }

    // 게시글 하나씩 조회
    @Override
    public BBoard readBBoardById(Long bPostId) {
        this.bBoardMapper.increaseBViewCount(bPostId); // 조회수
        return this.bBoardMapper.findById(bPostId);
    }

    // 게시글 수정
    @Override
    public void updateBBoard(BBoardForm bBoardForm) {
        BBoard bBoard = new BBoard();

        bBoard.setBPostId(bBoardForm.getBPostId());
        bBoard.setTitle(bBoardForm.getTitle());
        bBoard.setBContent(bBoardForm.getBContent());
        bBoard.setUpdatedAt(LocalDateTime.now());

        // 수정 DB
        this.bBoardMapper.updateBBoard(bBoard);
    }

    // 게시글 삭제
    @Override
    public void deleteBBoard(Long bPostId) {
        this.bBoardMapper.deleteBBoard(bPostId);
    }

}
