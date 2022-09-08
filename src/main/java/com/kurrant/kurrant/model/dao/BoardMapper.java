package com.kurrant.kurrant.model.dao;

import com.kurrant.kurrant.model.dto.BoardRequestDto;
import com.kurrant.kurrant.model.dto.BoardResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {
    void createBoard(BoardRequestDto boardRequestDto);
    List<BoardResponseDto> getAllBoard();
    BoardResponseDto getBoard(Long boardId);
}
