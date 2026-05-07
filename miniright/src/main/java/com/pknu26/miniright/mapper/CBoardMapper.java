package com.pknu26.miniright.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pknu26.miniright.dto.CBoard;
import com.pknu26.miniright.dto.PageRequest;

@Mapper
public interface CBoardMapper {

    // 전체 게시글 목록 조회 + 페이징
    List<CBoard> findAll(PageRequest pageRequest);

    // 페이징용 전체 게시글 개수 
    int countAll();

    // 특정 게시글 1개 조회
    CBoard findById(@Param("postId") Long postId);

    // 게시글 등록(Create)
    int insertCBoard(CBoard cBoard);

    // 게시글 수정(Update)
    int updateCBoard(CBoard cBoard);

    // 게시글 삭제(Delete)
    int deleteCBoard(@Param("postId") Long postId);

    // 조회수 증가
    int increaseViewCount(@Param("postId") Long postId);
}
