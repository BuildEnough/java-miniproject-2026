package com.pknu26.miniright.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.miniright.dto.BBoard;
import com.pknu26.miniright.dto.PageRequest;
import com.pknu26.miniright.mapper.BBoardMapper;
import com.pknu26.miniright.validation.BBoardForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BBoardServiceImpl implements BBoardService {

    private final BBoardMapper bBoardMapper;

    // 게시글 등록
    @Override
    public void createBBoard(BBoardForm bBoardForm) {
        BBoard bBoard = new BBoard();

        // 누락되었던 Category ID 및 User ID 설정 추가
        bBoard.setCategoryId(bBoardForm.getCategoryId());
        bBoard.setUserId(bBoardForm.getUserId());
        
        bBoard.setTitle(bBoardForm.getTitle()); 
        bBoard.setContents(bBoardForm.getContents());
        bBoard.setCreatedAt(LocalDateTime.now());

        this.bBoardMapper.insertBBoard(bBoard);
    }

    // 게시글 하나씩 조회
    @Override
    public BBoard readBBoardById(Long postId) {
        this.bBoardMapper.increaseBViewCount(postId);
        return this.bBoardMapper.findById(postId);
    }

    // 게시글 수정
    @Override
    public void updateBBoard(BBoardForm bBoardForm) {
        BBoard bBoard = new BBoard();

        bBoard.setPostId(bBoardForm.getPostId());
        bBoard.setTitle(bBoardForm.getTitle());
        bBoard.setContents(bBoardForm.getContents());
        bBoard.setUpdatedAt(LocalDateTime.now());

        this.bBoardMapper.updateBBoard(bBoard);
    }

    // 게시글 삭제
    @Override
    public void deleteBBoard(Long postId) {
        this.bBoardMapper.deleteBBoard(postId);
    }

    // 페이징된 게시글 목록 조회
    @Override
    public List<BBoard> readBBoardList(PageRequest pageRequest) {
        return this.bBoardMapper.selectBBoardList(pageRequest);
    }

    @Override
    public int getTotalCount() {
    return this.bBoardMapper.countBBoardList();
    }

}