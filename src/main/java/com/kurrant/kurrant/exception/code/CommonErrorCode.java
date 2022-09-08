package com.kurrant.kurrant.exception.code;

import com.kurrant.kurrant.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    MYBATIS_EXCEPTION(HttpStatus.BAD_REQUEST, "Property 가 잘못되었습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
