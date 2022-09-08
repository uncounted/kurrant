package com.kurrant.kurrant.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ArticleRequestDto {
    @NotNull
    private Long boardId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Builder
    public ArticleRequestDto(Long boardId, String title, String content) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }
}