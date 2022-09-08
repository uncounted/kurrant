package com.kurrant.kurrant.service;

import com.kurrant.kurrant.exception.ApiException;
import com.kurrant.kurrant.exception.code.ArticleErrorCode;
import com.kurrant.kurrant.model.dao.ArticleMapper;
import com.kurrant.kurrant.model.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final BoardService boardService;

    @Override
    public ArticleResponseDto getArticle(Long articleId) {
        updateArticleViewCount(articleId);
        return checkExistArticle(articleId);
    }

    @Override
    public void updateArticleViewCount(Long articleId) {
        articleMapper.updateArticleViewCount(articleId);
    }

    @Override
    public void deleteArticle(Long articleId) {
        checkExistArticle(articleId);
        articleMapper.deleteArticle(articleId);
    }

    @Override
    public List<ArticleResponseListDto> getAllArticle(Long boardId) {
        return checkArticleResponseListDto(articleMapper.getAllArticle(boardId));
    }

    @Override
    public List<ArticleResponseListDto> getAllArticleByBoardName(String keyword) {
        return checkArticleResponseListDto(articleMapper.getAllArticleByBoardName(keyword));
    }

    @Override
    public List<ArticleResponseListDto> getAllArticleByTitle(String keyword) {
        return checkArticleResponseListDto(articleMapper.getAllArticleByTitle(keyword));
    }

    @Override
    public List<ArticleResponseListDto> getAllArticleByDate(ArticleRequestByDateDto articleRequestByDateDto) {
        return checkArticleResponseListDto(articleMapper.getAllArticleByDate(articleRequestByDateDto));
    }

    @Override
    public ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto) {
        // 게시판 존재여부 검사
        boardService.checkExistBoard(articleRequestDto);

        // 정규식으로 태그 제거
        String contentString = articleRequestDto.getContent().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");

        // Request로 받은 Content(html)를 ContentHtml, ContentString 으로 분리하여 DB에 insert
        ArticleInsertDto insertDto = ArticleInsertDto.builder()
                .boardId(articleRequestDto.getBoardId())
                .title(articleRequestDto.getTitle())
                .contentHtml(articleRequestDto.getContent())
                .contentString(contentString)
                .build();
        articleMapper.createArticle(insertDto);

        return checkExistArticle(insertDto.getArticleId());
    }

    private ArticleResponseDto checkExistArticle(Long articleId) {
        ArticleResponseDto articleResponseDto = articleMapper.getArticle(articleId);
        if (articleResponseDto == null) {
            throw new ApiException(ArticleErrorCode.NON_EXIST_ARTICLE);
        } else {
            return articleResponseDto;
        }
    }

    private List<ArticleResponseListDto> checkArticleResponseListDto(List<ArticleResponseListDto> articleResponseListDtoList) {
        if (articleResponseListDtoList.isEmpty()) {
            throw new ApiException(ArticleErrorCode.NON_EXIST_ARTICLE);
        }
        return articleResponseListDtoList;
    }
}
