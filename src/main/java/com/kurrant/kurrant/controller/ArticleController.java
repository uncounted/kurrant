package com.kurrant.kurrant.controller;

import com.kurrant.kurrant.model.dto.ArticleRequestByDateDto;
import com.kurrant.kurrant.model.dto.ArticleRequestDto;
import com.kurrant.kurrant.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/article/{articleId}")
    public ResponseEntity<Object> getArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.getArticle(articleId));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<Object> getAllArticle(@PathVariable Long boardId) {
        return ResponseEntity.ok(articleService.getAllArticle(boardId));
    }

    @GetMapping("/article/search/boardName/{keyword}")
    public ResponseEntity<Object> getAllArticleByBoardName(@PathVariable String keyword) {
        return ResponseEntity.ok(articleService.getAllArticleByBoardName(keyword));
    }

    @GetMapping("/article/search/title/{keyword}")
    public ResponseEntity<Object> getAllArticleByTitle(@PathVariable String keyword) {
        return ResponseEntity.ok(articleService.getAllArticleByTitle(keyword));
    }

    @PostMapping("/article/search/date")
    public ResponseEntity<Object> getAllArticleByDate(@RequestBody ArticleRequestByDateDto articleRequestByDateDto) {
        return ResponseEntity.ok(articleService.getAllArticleByDate(articleRequestByDateDto));
    }

    @PostMapping("/article")
    public ResponseEntity<Object> createArticle(@Validated @RequestBody ArticleRequestDto articleRequestDto) {
        return ResponseEntity.ok(articleService.createArticle(articleRequestDto));
    }

    @DeleteMapping("/article/{articleId}")
    public ResponseEntity<Object> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
