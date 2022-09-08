package com.kurrant.kurrant.controller;

import com.kurrant.kurrant.model.dto.BoardRequestDto;
import com.kurrant.kurrant.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    public ResponseEntity<Object> getAllBoard() {
        return ResponseEntity.ok(boardService.getAllBoard());
    }

    @PostMapping("/board")
    public ResponseEntity<Object> createBoard(@Validated @RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(boardService.createBoard(boardRequestDto));
    }
}
