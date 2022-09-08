package com.kurrant.kurrant.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDto {
    private Long article_id;
    private String board_name;
    private LocalDateTime created_datetime;
    private Boolean is_pinned;
    private Integer view_count;
    private String title;
    private String content_html;
    private String content_string;
}
