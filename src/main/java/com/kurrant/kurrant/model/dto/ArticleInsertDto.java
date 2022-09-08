package com.kurrant.kurrant.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ArticleInsertDto {
    private Long articleId;
    @NotNull
    private Long boardId;

    @NotNull
    private String title;

    @NotNull
    private String contentHtml;

    @NotNull
    private String contentString;

    @Builder
    public ArticleInsertDto(Long boardId, String title, String contentHtml, String contentString) {
        this.boardId = boardId;
        this.title = title;
        this.contentHtml = contentHtml;
        this.contentString = contentString;
    }
}