package com.pknu26.miniright.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pknu26.miniright.dto.BBoard;
import com.pknu26.miniright.dto.PageRequest;

@Mapper
public interface BBoardMapper {

    // 전체 게시글 목록 조회
    List<BBoard> selectBBoardList(PageRequest pageRequest);

    // 특정 게시글 1개 조회
    BBoard findById(@Param("postId") Long postId);

    // 게시글 등록
    int insertBBoard(BBoard bBoard);

    // 게시글 수정
    int updateBBoard(BBoard bBoard);

    // 게시글 삭제
    int deleteBBoard(@Param("postId") Long postId);

    // 조회수 증가
    int increaseBViewCount(@Param("postId") Long postId);

    // 전체 게시글 수
    int countBBoardList();
}