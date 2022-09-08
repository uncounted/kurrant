package com.kurrant.kurrant.exception.code;

import com.kurrant.kurrant.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {
    DUPLICATED_BOARD(HttpStatus.CONFLICT, "중복된 게시판명입니다."),
    NON_EXIST_BOARD(HttpStatus.NOT_FOUND, "게시판이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
