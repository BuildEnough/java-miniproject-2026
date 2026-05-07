package com.pknu26.miniright.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.dto.PageRequest;
import com.pknu26.miniright.mapper.CBoardMapper;
import com.pknu26.miniright.validation.CBoardForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CBoardServiceImpl implements CBoardService {

    private final CBoardMapper cBoardMapper;

    // 전체 게시글 조회 -- 페이징 추가
    @Override
    public List<CBoard> readCBoardList(PageRequest pageRequest) {
        return this.cBoardMapper.findAll(pageRequest);
    }

    // 페이징용 전체 게시글 개수 조회
    @Override
    public int countAll() {
        return this.cBoardMapper.countAll();
    }

    @Override
    public CBoard getCBoard(Long postId) {
        return cBoardMapper.findById(postId);
    }

    // 게시글 등록
    @Override
    public void createCBoard(CBoardForm cBoardForm, Long userId) {
        CBoard cBoard = new CBoard();
        cBoard.setTitle(cBoardForm.getTitle());
        cBoard.setContents(cBoardForm.getContents());
        cBoard.setWriter(cBoardForm.getWriter());
        cBoard.setUserId(userId);

        // 익명 체크 안 하면 null -> 값 0 
        Integer anonymous = cBoardForm.getAnonymous();
        cBoard.setAnonymous(anonymous == null ? 0 : anonymous);

        // 게시글 DB 등록
        this.cBoardMapper.insertCBoard(cBoard);
    }

    // 게시글 하나씩 조회
    @Override
    public CBoard readCBoardById(Long postId) {
        this.cBoardMapper.increaseViewCount(postId);
        return this.cBoardMapper.findById(postId);
    }

    // 게시글 수정
    @Override
    public void updateCBoard(CBoardForm cBoardForm) {
        CBoard cBoard = new CBoard();
        cBoard.setPostId(cBoardForm.getPostId()); 
        cBoard.setTitle(cBoardForm.getTitle());
        cBoard.setContents(cBoardForm.getContents());
        cBoard.setWriter(cBoardForm.getWriter());

        // 수정 DB
        this.cBoardMapper.updateCBoard(cBoard);
    }

    // 수정 조회 메서드
    @Override
    public CBoard readCBoardForEdit(Long postId) {
        return this.cBoardMapper.findById(postId);
    }
    
    // 게시글 삭제
    @Override
    public void deleteCBoard(Long postId) {
        this.cBoardMapper.deleteCBoard(postId);
    }
}
