package com.store.rest.controllers;

import com.store.dao.UserRepository;
import com.store.exceptions.AppException;
import com.store.exceptions.AuthException;
import com.store.exceptions.JwtException;
import com.store.exceptions.MalformedJsonException;
import com.store.exceptions.RequestHeaderException;
import com.store.exceptions.ResourceNotFoundException;
import com.store.model.dto.exception.ExceptionDto;
import com.store.model.mapper.ExceptionMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.store.util.ExceptionConstants.ACCESS_DENIED;
import static com.store.util.ExceptionConstants.ACC_STATUS;
import static com.store.util.ExceptionConstants.BAD_CREDENTIALS;
import static com.store.util.ExceptionConstants.EXPIRED_JWT;
import static com.store.util.ExceptionConstants.INVALID_JWT;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
@Order(HIGHEST_PRECEDENCE)
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final ExceptionMapper exceptionMapper;
    private final UserRepository repository;


    @ExceptionHandler(AppException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleException(AppException ex) {
        log.error(ex.getMessage());
        final ExceptionDto errorResponse = exceptionMapper.toExceptionDto(ex);
        HttpStatus code = errorResponse.getStatus();
        return ResponseEntity.status(code)
                .contentType(APPLICATION_JSON)
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage());
        return handleException(new RequestHeaderException());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleException(new ResourceNotFoundException());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage());
        return handleException(new MalformedJsonException());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());

        if (ex instanceof BadCredentialsException) {
            log.info(repository.findAll().toString());
            return handleException(new AuthException(BAD_CREDENTIALS));
        }

        if (ex instanceof AccountStatusException) {
            return handleException(new AuthException(ACC_STATUS));
        }

        if (ex instanceof AccessDeniedException) {
            return handleException(new AuthException(ACCESS_DENIED));
        }

        if (ex instanceof SignatureException) {
            return handleException(new JwtException(INVALID_JWT));
        }

        if (ex instanceof ExpiredJwtException) {
            return handleException(new AuthException(EXPIRED_JWT));
        }

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

}
