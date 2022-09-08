package com.leonhard.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Getter
@Builder
public class ExceptionDto {

    private Date timestamp;
    private String message;
    private String details;
}
