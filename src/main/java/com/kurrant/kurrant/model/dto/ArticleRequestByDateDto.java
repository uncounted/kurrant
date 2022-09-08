package com.kurrant.kurrant.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleRequestByDateDto {
    private String startDate; //2022-01-23
    private String endDate; //2022-01-31

    @Builder
    public ArticleRequestByDateDto(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
