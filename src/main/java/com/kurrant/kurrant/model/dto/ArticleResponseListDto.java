package com.kurrant.kurrant.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseListDto {
    private Long article_id;
    private Long board_id;
    private String name_ko;
    private LocalDateTime created_datetime;
    private Boolean is_pinned;
    private Integer view_count;
    private String title;
}
