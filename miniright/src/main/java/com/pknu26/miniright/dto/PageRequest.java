package com.pknu26.miniright.dto;

import lombok.Data;

@Data
public class PageRequest {

    // 현재 페이지
    private int page = 1;

    // 한 페이지당 게시글 수
    private int size = 10;

    // 검색어
    private String keyword;

    // 시작일 yyyy-MM-dd
    private String startDate;

    // 종료일 yyyy-MM-dd
    private String endDate;

    // 카테고리
    private Long categoryId;

    public int getOffset() {
        return (page - 1) * size;
    }

    public void setPage(int page) {
        if (page < 1) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public void setSize(int size) {
        if (size < 1) {
            this.size = 10;
            return;
        }
        this.size = size;
    }
}