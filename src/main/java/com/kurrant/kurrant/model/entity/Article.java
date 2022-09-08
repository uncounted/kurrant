package com.kurrant.kurrant.model.entity;

import java.time.LocalDateTime;

public class Article {
    private Long articleId;
    private Long boardId;
    private LocalDateTime createdDatetime;
    private Boolean isPinned;
    private Integer viewCount;
    private String title;
    private String contentHtml;
    private String contentString;
}
