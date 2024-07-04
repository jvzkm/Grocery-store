package com.store.model.mapper;

import com.store.exceptions.AppException;
import com.store.model.dto.exception.ExceptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExceptionMapper {

    ExceptionDto toExceptionDto(AppException e);
}