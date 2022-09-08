package com.kurrant.kurrant.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
    private Long boardId;
    @NotNull
    private String nameKo;

    @Builder
    public BoardRequestDto(String nameKo) {
        this.nameKo = nameKo;
    }
}
