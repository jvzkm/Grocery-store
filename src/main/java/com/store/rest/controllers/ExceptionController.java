package com.store.rest.controllers;

import com.store.exceptions.AppException;
import com.store.exceptions.MalformedJsonException;
import com.store.model.dto.exception.ExceptionDto;
import com.store.model.mapper.ExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final ExceptionMapper exceptionMapper;


    @ExceptionHandler(AppException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleException(AppException ex) {
        final ExceptionDto errorResponse = exceptionMapper.toExceptionDto(ex);
        HttpStatus code = errorResponse.getStatus();
        return ResponseEntity.status(code)
                .contentType(APPLICATION_JSON)
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleException(new MalformedJsonException());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }
}
