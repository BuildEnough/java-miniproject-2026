package com.pknu26.miniright.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pknu26.miniright.dto.CBoard;

@Mapper
public interface CBoardMapper {

    // 전체 게시글 목록 조회
    List<CBoard> findAll();

    // 특정 게시글 1개 조회
    CBoard findById(@Param("cPostId") Long cPostId);

    // 게시글 등록(Create)
    int insertCBoard(CBoard cBoard);

    // 게시글 수정
    int updateCBoard(CBoard cBoard);

    // 게시글 삭제
    int deleteCBoard(@Param("cPostId") Long cPostId);

    // 조회수 증가
    int increaseCViewCount(@Param("cPostId") Long cPostId);
}
