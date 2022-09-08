package com.kurrant.kurrant.service;

import com.kurrant.kurrant.model.dto.ArticleRequestByDateDto;
import com.kurrant.kurrant.model.dto.ArticleRequestDto;
import com.kurrant.kurrant.model.dto.ArticleResponseDto;
import com.kurrant.kurrant.model.dto.ArticleResponseListDto;

import java.util.List;

public interface ArticleService {
    ArticleResponseDto getArticle(Long articleId);
    List<ArticleResponseListDto> getAllArticle(Long boardId);
    List<ArticleResponseListDto> getAllArticleByBoardName(String keyword);
    List<ArticleResponseListDto> getAllArticleByTitle(String keyword);
    List<ArticleResponseListDto> getAllArticleByDate(ArticleRequestByDateDto articleRequestByDateDto);
    void updateArticleViewCount(Long articleId);
    ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto);
    void deleteArticle(Long articleId);
}
