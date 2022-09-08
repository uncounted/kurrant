package com.kurrant.kurrant.model.dao;

import com.kurrant.kurrant.model.dto.*;
import com.kurrant.kurrant.model.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Mapper
public interface ArticleMapper {
    ArticleResponseDto getArticle(Long articleId);
    List<ArticleResponseListDto> getAllArticle(Long boardId);
    List<ArticleResponseListDto> getAllArticleByBoardName(String keyword);
    List<ArticleResponseListDto> getAllArticleByTitle(String keyword);
    List<ArticleResponseListDto> getAllArticleByDate(ArticleRequestByDateDto requestByDateDto);
    void updateArticleViewCount(Long articleId);
    void createArticle(ArticleInsertDto articleInsertDto);
    void deleteArticle(Long articleId);
}
