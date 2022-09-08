package com.kurrant.kurrant.service;


import com.kurrant.kurrant.model.dto.ArticleRequestDto;
import com.kurrant.kurrant.model.dto.BoardRequestDto;
import com.kurrant.kurrant.model.dto.BoardResponseDto;

import java.util.List;

public interface BoardService {
    List<BoardResponseDto> getAllBoard();
    BoardResponseDto getBoard(Long boardId);
    BoardResponseDto createBoard(BoardRequestDto boardRequestDto);

    void checkExistBoard(ArticleRequestDto articleRequestDto);
}
