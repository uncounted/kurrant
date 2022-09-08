package com.kurrant.kurrant.exception.code;

import com.kurrant.kurrant.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ArticleErrorCode implements ErrorCode {
    NON_EXIST_ARTICLE(HttpStatus.NOT_FOUND, "게시글이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
