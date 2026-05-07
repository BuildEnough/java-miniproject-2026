package com.pknu26.miniright.service;

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

    // 검색 + 페이징 목록 조회
    @Override
    public List<BBoard> readBBoardList(PageRequest pageRequest) {
        return this.bBoardMapper.selectBBoardList(pageRequest);
    }

    // 검색 조건에 맞는 전체 게시글 수
    @Override
    public int getTotalCount(PageRequest pageRequest) {
        return this.bBoardMapper.countBBoardList(pageRequest);
    }

    // 상세보기용: 조회수 증가 O
    @Override
    public BBoard readBBoardById(Long postId) {
        this.bBoardMapper.increaseBViewCount(postId);
        return this.bBoardMapper.findById(postId);
    }

    // 수정/삭제 권한 확인용: 조회수 증가 X
    @Override
    public BBoard readBBoardForEdit(Long postId) {
        return this.bBoardMapper.findById(postId);
    }

    // 게시글 등록
    @Override
    public void createBBoard(BBoardForm bBoardForm) {
        BBoard bBoard = new BBoard();

        bBoard.setCategoryId(bBoardForm.getCategoryId());
        bBoard.setUserId(bBoardForm.getUserId());
        bBoard.setTitle(bBoardForm.getTitle());
        bBoard.setContents(bBoardForm.getContents());
        bBoard.setImagePath(bBoardForm.getImagePath());

        this.bBoardMapper.insertBBoard(bBoard);
    }

    // 게시글 수정
    @Override
    public void updateBBoard(BBoardForm bBoardForm) {
        BBoard bBoard = new BBoard();

        bBoard.setPostId(bBoardForm.getPostId());
        bBoard.setCategoryId(bBoardForm.getCategoryId());
        bBoard.setTitle(bBoardForm.getTitle());
        bBoard.setContents(bBoardForm.getContents());
        bBoard.setImagePath(bBoardForm.getImagePath());

        this.bBoardMapper.updateBBoard(bBoard);
    }

    // 게시글 삭제
    @Override
    public void deleteBBoard(Long postId) {
        this.bBoardMapper.deleteBBoard(postId);
    }
}