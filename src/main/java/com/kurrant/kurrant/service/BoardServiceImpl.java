package com.kurrant.kurrant.service;

import com.kurrant.kurrant.exception.ApiException;
import com.kurrant.kurrant.exception.code.BoardErrorCode;
import com.kurrant.kurrant.exception.code.CommonErrorCode;
import com.kurrant.kurrant.model.dao.BoardMapper;
import com.kurrant.kurrant.model.dto.ArticleRequestDto;
import com.kurrant.kurrant.model.dto.BoardRequestDto;
import com.kurrant.kurrant.model.dto.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;

    @Override
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        try {
            boardMapper.createBoard(boardRequestDto);
        } catch (DuplicateKeyException e) {
            log.info(BoardErrorCode.DUPLICATED_BOARD.getMessage());
            throw new ApiException(BoardErrorCode.DUPLICATED_BOARD);
        } catch (RuntimeException e) {
            log.info(CommonErrorCode.MYBATIS_EXCEPTION.getMessage());
            throw new ApiException(CommonErrorCode.MYBATIS_EXCEPTION);
        }

        return checkExistBoard(boardRequestDto.getBoardId());
    }

    @Override
    public List<BoardResponseDto> getAllBoard() {
        try {
            return boardMapper.getAllBoard();
        } catch (RuntimeException e) {
            throw new ApiException(BoardErrorCode.NON_EXIST_BOARD);
        }
    }

    @Override
    public BoardResponseDto getBoard(Long boardId) {
        return boardMapper.getBoard(boardId);
    }

    public void checkExistBoard(ArticleRequestDto articleRequestDto) {
        if (boardMapper.getBoard(articleRequestDto.getBoardId()) == null) {
            throw new ApiException(BoardErrorCode.NON_EXIST_BOARD);
        }
    }

    private BoardResponseDto checkExistBoard(Long boardId) {
        BoardResponseDto boardResponseDto = boardMapper.getBoard(boardId);
        if (boardResponseDto == null) {
            throw new ApiException(BoardErrorCode.NON_EXIST_BOARD);
        }
        return boardResponseDto;
    }
}
