package com.pknu26.miniright.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.miniright.dto.BBoard;

@Mapper
public interface BBoardMapper {

    // 전체 게시글 목록 조회
    List<BBoard> findAll();

    // 특정 게시글 1개 조회
    BBoard findById(Long bPostId);

    // 게시글 등록(Create)
    int insertBBoard(BBoard bBoard);

    // 게시글 수정
    int updateBBoard(BBoard bBoard);

    // 게시글 삭제 
    int deleteBBoard(Long bPostId);

    // 조회수 증가
    int increaseBViewCount(Long bPostId);
}
